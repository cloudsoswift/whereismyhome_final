<template>
    <div>
        <div id="map" style="width: 100%; height: 700px"></div>
    </div>
</template>

<script>
import http from "@/util/http";
import { mapState } from "vuex";
export default {
    name: 'ApartMap',
    props: ["addr"],
    data() {
        return {
            map: null,
            markerList: [],
            openedInfoWindow: {},
            commercialMarkerList: [],
        };
    },

    mounted() {
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement("script");
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src =
                "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&libraries=services&appkey=70a44449fcda7d5ceeda61a8862c1533";
            document.head.appendChild(script);
        }
    },

    methods: {
        initMap() {
            var mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 6, // 지도의 확대 레벨
            };

            // 지도를 생성합니다
            this.map = new kakao.maps.Map(mapContainer, mapOption);

            // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
            var mapTypeControl = new kakao.maps.MapTypeControl();
            // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
            var zoomControl = new kakao.maps.ZoomControl();

            // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
            this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
            this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

            // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
            if (navigator.geolocation) {
            // GeoLocation을 이용해서 접속 위치를 얻어옵니다
            navigator.geolocation.getCurrentPosition( (position) => {
                var lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도

                var myLatLng = new kakao.maps.LatLng(lat, lon) // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

                // 지도 중심좌표를 접속위치로 변경합니다
                this.map.setCenter(myLatLng);
            });
            } else {
            // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
                let myLatLng = new kakao.maps.LatLng(37.5012743, 127.039585) // 위치
                this.displayMarker(myLatLng, "");
            }
        },
        // 지도에 마커와 인포윈도우를 표시하는 함수입니다
        displayMarker(myLatLng, message, markerImage) {
        // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: this.map,
                position: myLatLng,
                image: markerImage, // 마커이미지 설정
            });
            if (message) {
                var iwContent = `
                <li class="list-group-item mb-1">
                    <div class="card" style="min-width: 18rem">
                    <h5 class="card-header mb-3 text-center">${message.apartmentName}</h5>
                    <div class="card-body text-center">
                        <h5 class="card-title mb-3 text-center">주변 상권 검색</h5>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="checkedValue" id="convenience" value="편의점">
                            <label class="form-check-label" for="convenience">편의점</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="checkedValue" id="hospital" value="건강">
                            <label class="form-check-label" for="hospital">병원</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="checkedValue" id="restaurant" value="음식">
                            <label class="form-check-label" for="restaurant">음식점</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="checkedValue" id="cafe" value="카페">
                            <label class="form-check-label" for="cafe">카페</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="checkedValue" id="hairshop" value="미용실">
                            <label class="form-check-label" for="hairshop">미용실</label>
                        </div>
                        <button class="btn btn-outline-primary" id="searchCommercial">검색</button>
                    </div>
                    </div>
                </li>
                    `
                    
                    // 인포윈도우를 생성합니다
                    var infowindow = new kakao.maps.InfoWindow({
                        content: iwContent,
                    });
                }
                this.markerList.push(marker);
                // 마커에 마우스클릭 이벤트를 등록합니다
                kakao.maps.event.addListener(marker, "click", () => {
                    // 마커에 마우스클릭 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
                    // 인포윈도우가 안 켜져 있는 경우 ( 켜야하는 경우 )
                    if(infowindow.getMap() == null){
                        //이미 켜져있는 다른 infoWindow 있으면 close
                        if(this.openedInfoWindow.getMap != undefined){
                            this.openedInfoWindow.close();
                        }
                        this.openedInfoWindow = infowindow;
                        this.goCenter(myLatLng.getLat(), myLatLng.getLng(), 2);
                        infowindow.open(this.map, marker);
                        // 인포윈도우 내 radiobutton과 button에 대한 이벤트 리스너 등록
                        document.querySelector("#searchCommercial").addEventListener("click", ()=>{
                            const radioList = document.querySelectorAll("[name=checkedValue]")
                            let value = "";
                            radioList.forEach((radio)=>{
                                if(radio.checked){
                                    value = radio.value;
                                }
                            })
                            http.get(`/apart/list/${myLatLng.getLng()}/${myLatLng.getLat()}/${value}`)
                            .then(({data, status})=>{
                                switch(status){
                                    case 200:
                                        infowindow.close();
                                        this.mapCommercialMarker(data);
                                        break;
                                    case 204:
                                        alert(`${value}가 근방에 존재하지 않습니다.`);
                                        break;
                                    case 500:
                                        //HttpStatus.INTERNAL_SERVER_ERROR
                                        alert("서버와 통신중 에러가 발생했습니다.");
                                        this.$router.push("/");
                                        break;
                                }
                            });
                        })
                    } else {
                        infowindow.close();
                    }
            });
        },
        mapMarker(address, data) {
            this.hideMarker();
            this.hideCommercialMarker();
            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();
            // 마커 이미지의 이미지 주소입니다
            var imageSrc = require("@/assets/img/markers/marker.png");
            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(48, 48);
            // 마커 이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            geocoder.addressSearch(address, (result, status) => {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {
                    // 검색 주소 기준으로 지도 중심 이동
                    this.goCenter(result[0].y, result[0].x);
                }
            });
            // 매매 정보 주소를 좌표로 변환하기
            data.forEach((apt) => {
                this.displayMarker(new kakao.maps.LatLng(apt.lat, apt.lng), apt, markerImage);
            });

        },
        mapCommercialMarker(data){
            this.hideCommercialMarker();
            var imageSrc = require("@/assets/img/markers/shop.png");
            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(48, 48);
            // 마커 이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            data.forEach((shop) => {
                this.displayCommercialMarker(new kakao.maps.LatLng(shop.lng, shop.lat), shop, markerImage);
            });
        },
        displayCommercialMarker(myLatLng, shop, markerImage){
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: this.map,
                position: myLatLng,
                image: markerImage, // 마커이미지 설정
            });
            var iwContent = `
                <div class="card rounded-5 shadow-sm" style="min-width: 18rem">
                    <div class="card-body">
                        <h5 class="card-title text-center">${shop.name} ${shop.branch}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">${shop.info}</h6>
                        <p class="card-text mb-3">${shop.address} ${shop.floorNo == ""? "": shop.floorNo + "층"}</p>
                    </div>
                    <div class="tooltip-arrow"></div>
                </div>
            `
                
            // 인포윈도우를 생성합니다
            var overlay = new kakao.maps.CustomOverlay({
                content: iwContent,
                position: myLatLng,
                yAnchor: 1.4,
            });
            this.commercialMarkerList.push(marker);
            // 마커에 마우스클릭 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'mouseover', () => {
            // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
                overlay.setMap(this.map);
            });

            // 마커에 마우스아웃 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'mouseout', () => {
                // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
                overlay.setMap(null);
            });
        },
        goCenter(lat, lng, level=5) {
            var myLatLng = new kakao.maps.LatLng(lat, lng);
            this.map.setCenter(myLatLng);
            this.map.setLevel(level);
        },
        hideMarker() {
            for(let i=0; i < this.markerList.length; i++){
                this.markerList[i].setMap(null);
            }
            this.markerList = [];
        },
        hideCommercialMarker(){
            for(let i=0; i < this.commercialMarkerList.length; i++){
                this.commercialMarkerList[i].setMap(null);
            }
            this.commercialMarkerList = [];
        }
    },
    computed: {
            ...mapState(["houses", "centerPos"]),
        },
    watch: {
        houses(newValue) {
            this.mapMarker(this.addr, newValue)
        },
        centerPos(newValue){
            this.goCenter(newValue.lat, newValue.lng, 2);
        }
    },
};
</script>

<style lang="scss" scoped>

</style>