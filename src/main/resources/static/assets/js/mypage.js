// 마이페이지

// 회원정보 수정하기
const modifyButton = document.querySelector("#modifyBtn");
modifyButton.addEventListener("click", function () {
  // const userId = document.querySelector("Id").value;
  // const joinDate = document.querySelector("#JoinDate").value;
  const userPassword = document.querySelector("#userPassword").value;
  const userName = document.querySelector("#Name").value;
  const userAddress = document.querySelector("#Address").value;
  const userPhone = document.querySelector("#Phone").value;

  const data = {
    Password: userPassword,
    Name: userName,
    Address: userAddress,
    Phone: userPhone
  }

  fetch('/user/', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log('Success:', data);
      alert("회원 정보가 수정되었습니다.")
    })
    .catch((error) => {
      console.error('Error:', error);
      alert("회원 정보 수정에 오류가 발생했습니다.")
    });
 
});


// 회원정보 삭제하기
const deleteButton = document.querySelector("#deleteBtn");
deleteButton.addEventListener("click", () => {
  fetch('/user/', {
    method: 'DELETE'
  })
    .then((response) => response.json())
    .then((data) => {
      console.log('Success:', data);
    })
    .catch((error) => {
      console.error('Error:', error);
      alert("회원 정보 삭제에 오류가 발생했습니다.");
      return;
    });
  
  alert("회원 정보가 삭제되었습니다.");
  location.href = "/index";


})