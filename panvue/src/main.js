import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import axios from "axios";
import Avue from '@smallwei/avue';
import '@smallwei/avue/lib/index.css';
import ElementUI from '@/theme/index.css';
import './theme/index.css'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import VuePreview from 'vue-preview'
Vue.use(VuePreview, {
  mainClass: 'pswp--minimal--dark',
  barsSize: {top: 0, bottom: 0},
  captionEl: false,
  fullscreenEl: false,
  shareEl: false,
  bgOpacity: 0.85,
  tapToClose: true,
  tapToToggleControls: false
})
Vue.use(Viewer);

Viewer.setDefaults({
  Options: {
    'inline':true,
    'button':true, //右上角按钮
    "navbar": true, //底部缩略图
    "title": true, //当前图片标题
    "toolbar": true, //底部工具栏
    "tooltip": true, //显示缩放百分比
    "movable": true, //是否可以移动
    "zoomable": true, //是否可以缩放
    "rotatable": true, //是否可旋转
    "scalable": true, //是否可翻转
    "transition": true, //使用 CSS3 过度
    "fullscreen": true, //播放时是否全屏
    "keyboard": true, //是否支持键盘
    "url": "data-source",
  }
})
Vue.use(Avue);
Vue.config.productionTip = false
/* eslint-disable no-new */
Vue.use(ElementUI)

Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
