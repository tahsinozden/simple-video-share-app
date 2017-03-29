import Vue from 'vue'
import VueResource from 'vue-resource'
import App from './App.vue'
// import RandomVideo from './RandomVideo.vue'

Vue.use(VueResource);

// Require dependencies 
var VueCookie = require('vue-cookie');
// Tell Vue to use the plugin 
Vue.use(VueCookie);

export const eventBus = new Vue();

new Vue({
  el: '#app',
  render: h => h(App)
})
