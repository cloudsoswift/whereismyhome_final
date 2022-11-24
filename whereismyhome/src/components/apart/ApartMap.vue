<template>
    <div>
        <div id="map" style="width: 100%; height: 700px"></div>
    </div>
</template>

<script>
import { mapState } from "vuex";
export default {
    name: 'ApartMap',
    props: ["addr"],
    data() {
        return {
            map: null,
            markerList: [],
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
                    <div class="card-body">
                        <h5 class="card-title mb-3">${message.apartmentName}</h5>
                        <h6 class="card-subtitle mb-1">거래금액: ${message.dealAmount}만원</h6>
                        <p class="m-0 text-muted">면적: ${message.area}</p>
                        <p class="m-0 text-muted">층: ${message.date}</p>
                        <p class="card-text">${message.date}</p>
                        </div>
                        </div>
                        </li>
                    `, // 인포윈도우에 표시할 내용
                    iwRemoveable = true;
                    
                    // 인포윈도우를 생성합니다
                    var infowindow = new kakao.maps.InfoWindow({
                        content: iwContent,
                        removable: iwRemoveable,
                    });
                }
                
                this.markerList.push(marker);
                // 마커에 마우스오버 이벤트를 등록합니다
                kakao.maps.event.addListener(marker, "mouseover", () => {
                // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
                infowindow.open(this.map, marker);
            });
    
            // 마커에 마우스아웃 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, "mouseout", function () {
                // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
                infowindow.close();
            });
        },
        mapMarker(address, data) {
            this.hideMarker();
            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();
            // 마커 이미지의 이미지 주소입니다
            var imageSrc = require("@/assets/img/marker.png");
            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(48, 48);
            // 마커 이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            // 검색 주소 기준으로 지도 중심 이동
            geocoder.addressSearch(address, (result, status) => {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {
                    this.goCenter(result[0].y, result[0].x);
                }
            });
            // 매매 정보 주소를 좌표로 변환하기
            data.forEach((apt) => {
                this.displayMarker(new kakao.maps.LatLng(apt.lat, apt.lng), apt, markerImage);
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