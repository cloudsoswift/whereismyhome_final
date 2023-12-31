import Vue from "vue";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faBookmark as fasBookmark,
  faArrowLeft,
  faAnglesUp,
  faAnglesDown,
  faBuilding,
} from "@fortawesome/free-solid-svg-icons";
import { faBookmark as farBookmark } from "@fortawesome/free-regular-svg-icons";
library.add(fasBookmark, farBookmark, faArrowLeft, faAnglesUp, faAnglesDown, faBuilding);

Vue.component("font-awesome-icon", FontAwesomeIcon);
