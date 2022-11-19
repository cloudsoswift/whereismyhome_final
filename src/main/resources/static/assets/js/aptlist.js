let date = new Date();

///////////////////////////////// 시도, 구군, 동, 년도, 월 정보 ////////////////////////////////
// 브라우저가 열리면 시도정보 얻기.
window.onload = function () {
  sendRequest("sido", "1");

  // 년도 정보 초기화
  let yearEl = document.querySelector("#year");
  let yearOpt = `<option value="">매매년도선택</option>`;
  let year = date.getFullYear();
  for (let i = year; i > year - 20; i--) {
    yearOpt += `<option value="${i}">${i}년</option>`;
  }
  yearEl.innerHTML = yearOpt;
};

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#sido").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    let regcode = this[this.selectedIndex].value;
    sendRequest("gugun", regcode);
  } else {
    initOption("gugun");
    initOption("dong");
  }
});

// 구군이 바뀌면 동정보 얻기.
document.querySelector("#gugun").addEventListener("change", function () {
  // 동정보 얻기
  if (this[this.selectedIndex].value) {
    let regcode = this[this.selectedIndex].value;
    sendRequest("dong", regcode);
  } else {
    initOption("dong");
  }
});

// 서버로 요청 보내기
function sendRequest(selid, regcode) {
  // const url = HTTP + "/apart";
  // let params = "act=" + selid + "&code=" + regcode;
  // let path = `${url}?${params}` addOption(selid, data)
  const path = `/area/${selid}/${regcode}`;

  fetch(path)
    .then((response) => response.json())
    .then((data) => {
      addOption(selid, data);
    });
}

// 서버에서 요청 받아 옵션 추가하기
function addOption(selid, data) {
  let interBtn = document.querySelector("#inter-sido-i");
  let opt = ``;

  initOption(selid);
  switch (selid) {
    case "sido":
      opt += `<option value="">시도선택</option>`;
      data.forEach(function (regcode) {
        opt += `
          <option value="${regcode.sido}">${regcode.sidoname}</option>
          `;
      });
      break;
    case "gugun":
      // 관심 지역 처리
      interBtn.classList.remove("bi-star-fill");
      interBtn.classList.add("bi-star");

      opt += `<option value="">구군선택</option>`;
      data.forEach(function (regcode) {
        opt += `
            <option value="${regcode.gugun}" data-no="${regcode.interest_no}">${regcode.gugunName}</option>
            `;
      });
      break;
    case "dong":
      // 관심 지역 처리
      isInterestedRegion = data[0].interest_no > -1 ? true : false;
      if (isInterestedRegion) {
        interBtn.classList.add("bi-star-fill");
        interBtn.classList.remove("bi-star");
      } else {
        interBtn.classList.remove("bi-star-fill");
        interBtn.classList.add("bi-star");
      }
      // 동선택창
      opt += `<option value="">동선택</option>`;
      data.forEach(function (regcode) {
        opt += `
          <option value="${regcode.dongcode}">${regcode.dongname}</option>
          `;
      });
  }
  document.querySelector(`#${selid}`).innerHTML = opt;
}

// select 초기화
function initOption(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
}

// 년도 바뀌면 월 정보 얻기
document.querySelector("#year").addEventListener("change", function () {
  let month = date.getMonth() + 1;
  let monthEl = document.querySelector("#month");
  let monthOpt = `<option value="">매매월선택</option>`;
  let yearSel = document.querySelector("#year");
  let m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
  for (let i = 1; i < m; i++) {
    monthOpt += `<option value="${i < 10 ? "0" + i : i}">${i}월</option>`;
  }
  monthEl.innerHTML = monthOpt;
});

/////////////////////////////////////////////////// 아파트 매매 정보 검색 ///////////////////////////////////
// 검색하기 버튼 클릭되면 서버로 보내기.
document.querySelector("#list-btn").addEventListener("click", () => {
  let sidoSel = document.querySelector("#sido");
  let gugunSel = document.querySelector("#gugun");
  let dongSel = document.querySelector("#dong");
  let yearSel = document.querySelector("#year");
  let monthSel = document.querySelector("#month");

  // 모두 선택했는지 확인
  if (
    !sidoSel[sidoSel.selectedIndex].value ||
    !gugunSel[gugunSel.selectedIndex].value ||
    !dongSel[dongSel.selectedIndex].value ||
    !yearSel[yearSel.selectedIndex].value ||
    !monthSel[monthSel.selectedIndex].value
  ) {
    alert("모두 선택해주세요!");
    return;
  }

  // 서버로 보낼 데이터
  const addr = dongSel[dongSel.selectedIndex].value;
  const y = yearSel[yearSel.selectedIndex].value;
  const m = monthSel[monthSel.selectedIndex].value;

  const URL = "/apart/list";
  const params = `/${addr}/${y}/${m}`;
  let path = `${URL}${params}`;
  // 선택한 위치 (시도 + 구군 + 동)
  let address =
    sidoSel[sidoSel.selectedIndex].textContent +
    " " +
    gugunSel[gugunSel.selectedIndex].textContent +
    " " +
    dongSel[dongSel.selectedIndex].textContent;

  // 서버로 데이터 보내기
  fetch(path)
    .then((response) => response.json())
    .then((data) => {
      mapMarker(address, data); // 지도 생성
      makeList(data); // 검색 결과 리스트 생성
    });
});

////////////////////////////////////////// 검색 결과 //////////////////////////////////////////////
// 매매 검색 결과 리스트 생성
function makeList(data) {
  document.querySelector("#aptlist").setAttribute("style", "display: ;");
  let tbody = document.querySelector("#aptlist");

  tbody.innerHTML = `
    <h3>거래 정보</h3>
    <ul class="list-group list-group-flush">
  `;

  // 거래 정보마다 li 생성
  data.forEach((apt) => {
    let className = "bi-star-fill";
    if (apt["like_id"] < 0) {
      className = "bi-star";
    }

    tbody.innerHTML += `
    <li class="list-group-item mb-1">
    	<a href="javascript:goCenter(${apt.lng}, ${apt.lat})" style="color:black;">
      <div class="card" style="min-width: 18rem">
        <div class="card-body">
          <div class="row">
            <div class="col-lg-9 col-sm-12 text-lg-start text-center">
              <h5 class="card-title mb-3">${apt.apartmentName}</h5>
            </div>
            <div class="col-lg-2 col-sm-12 text-lg-end text-center ms-3">
              <button type="button" class="inter-apt btn" style="width: 40px">
                <i class="bi ${className}" data-code="${apt.aptCode}" data-no="${apt.no}"></i>
              </button>
            </div>
          </div>
          <h6 class="card-subtitle mb-1">거래금액: ${apt.dealAmount}만원</h6>
          <p class="m-0 text-muted">면적: ${apt.area}</p>
          <p class="m-0 text-muted">층: ${apt.floor}</p>
          <p class="card-text">${apt.date}</p>
        </div>
      </div>
      </a>
    </li>
        `;
  });
  tbody.innerHTML += `</ul>`;
}

///////////////////////////////////////// 관심 거래, 지역 //////////////////////////////////
// 매매 거래 정보 관심버튼 이벤트
document.querySelector("#aptlist").addEventListener("click", (e) => {
  if (e.target.tagName !== "I") {
    return;
  }

  const i_tag = e.target;
  i_tag.classList.toggle("bi-star-fill");
  i_tag.classList.toggle("bi-star");

  /*
   * url+params = REST URI
   * no=시,동까지 코드
   * method= POST 등록, DELETE 삭제
   */
  const url = "/apart";
  const no = i_tag.dataset.no;
  const method = i_tag.classList.contains("bi-star-fill") ? "POST" : "DELETE";
  const params = `/like/${no}`;
  let config = {
    method: method,
  };

  let path = `${url}${params}`;
  fetch(path, config).then((response) => response.json());
});

// 관심 지역 설정 (검색하기 옆에 관심 버튼)
document.querySelector("#inter-sido").addEventListener("click", function () {
  // 관심 선택한 위치 (시도 + 구군 )
  let sidoSel = document.querySelector("#sido");
  let gugunSel = document.querySelector("#gugun");

  // 모두 선택했는지 확인
  if (!sidoSel[sidoSel.selectedIndex].value || !gugunSel[gugunSel.selectedIndex].value) {
    alert("모두 선택해주세요!");
    return;
  }

  // 클래스 변경
  let button = document.querySelector("#inter-sido-i");
  button.classList.toggle("bi-star-fill");
  button.classList.toggle("bi-star");

  /*
   * act=서블렛이름
   * code=시,동까지 코드
   * method= POST 등록, DELETE 삭제
   */

  let url = "/area";
  let code = gugunSel[gugunSel.selectedIndex].value;
  let method = button.classList.contains("bi-star-fill") ? "POST" : "DELETE";
  let params = `/like/${code}`;
  let config = {
    method: method,
  };

  let path = `${url}${params}`;
  fetch(path, config).then((response) => response.json());
});
