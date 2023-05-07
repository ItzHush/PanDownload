<template>
  <div class="AsideBar" >
    <div
      class="barItem"
      :class="selectIndex === index ? 'select' : ''"
      v-for="(item, index) in asideBarData"
      :key="index"
      :index = "index+''"
      @click="clickAsideBarItem(index)"
    >
      <i :class="'el-icon-' + item.icon"> </i>

      <span >{{ item.name }}</span>
    </div>

<!--      这里放个人信息-->
    <div style="position: absolute;bottom: 0;height: 300px;width: 100%;border-top: 13px">
        <div class="progress">
        <span>{{formLabelAlign.usespace}} / {{formLabelAlign.space}}GB &nbsp;&nbsp;</span>
        <el-progress :text-inside="true" :stroke-width="22" :percentage= Number(formLabelAlign.sp)></el-progress>
      </div>
        <div class = lines></div>
        <div class="list"  v-if="show" @mouseenter="enter1" @mouseleave="leave1">
            <el-button class="myButton" @click="userInfo">个人中心</el-button>
            <br>
            <el-button class="myButton" @click="about">关于网盘</el-button>
            <br>
            <el-button class="myButton" @click="logout">退出登录</el-button>
        </div>
        <span class="infoName" style="margin-top: 14%; margin-right: 50%">{{formLabelAlign.nickname}}</span>

    <div  @mouseenter="enter" @mouseleave="leave">
        <el-avatar shape="circle" :size="50"
                   :src="require('D:/A_panDownload/avatar/'+formLabelAlign.avatar)"></el-avatar>
    </div>


    </div>

  </div>
</template>

<script>
import router from "@/router";
import axios from "axios";
import about from "@/components/About.vue";

export default {
  name: "AsideBar",
    computed: {

    },
  data() {
    return {
        show: false,
        visible: false,
      selectIndex: 0,
        formLabelAlign: {
            username: '',
            nickname: 'Nick',
            password: 'qweqwe',
            space: '默认',
            usespace: '389MB',
            avatar: '',
            newavatarname: '',
            sp: '',
        }
    };
  },
  props: {
    // name   path   icon
    asideBarData: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  methods: {

    clickAsideBarItem(index) {
      if (this.selectIndex == index) return;
      this.selectIndex = index;
      // 路由跳转
      if (this.asideBarData[index].componentName) {
        router.push({
          name: this.asideBarData[index].componentName,
          params: this.asideBarData[index].params,
        })
      } else {
        this.$router.push(this.asideBarData[index].path);
      }
    },
      enter() {
        this.show = true
      },
      leave() {
        setTimeout(() => {
            this.close()
        }, 500);
      },
      enter1() {
        this.visible = true;
      },
      leave1() {
          this.visible = false;
          setTimeout(() => {
              this.close()
          }, 300);
      },
      close(){
        if (this.visible === false){
            this.show = false;
        }
      },
      userInfo() {
          router.push('/userinfo')
      },
      logout() {
          localStorage.clear();
          sessionStorage.clear();
          router.push('/')
      },
      about() {
          router.push('/about')
      },
  },
    watch: {
        $route(to, from) {
            window.location.reload(); //监测到路由发生跳转时刷新一次页面
        },

    },
  created() {
    this.selectIndex = this.asideBarData.findIndex(
      (item) => item.path.slice(1) == this.$route.path.split("/")[1]
    );
      axios.get('http://182.84.10.33:8181/user/getIndexInfo').then((resp) => {
          if (resp.data.success) {
              this.formLabelAlign.avatar = resp.data.message[0];
              this.formLabelAlign.usespace = resp.data.message[1];
              this.formLabelAlign.nickname = resp.data.message[2];
              this.formLabelAlign.space = resp.data.message[3];
              this.formLabelAlign.sp = resp.data.message[7];
              this.formLabelAlign.newavatarname = resp.data.message[8];
              console.log(this.formLabelAlign)
          }
      })
  },
};
</script>

<style scoped>
i {
  font-size: 24px;
  margin-right: 10px;
}

.barItem {
  margin: 20px 10px;
  padding: 12px  10% ;
  border-radius: 12px;
  cursor: pointer;
}
.infoName{
    font-size: 18px;
    letter-spacing: 1px;
    display: flex;
    float: right;
}
.el-avatar {
    position: absolute; right: 18%;
    bottom: 6%;
}


.barItem:hover {
  background-color: #ececee;
}

.select {
  background-color: rgb(231, 231, 231) !important;
}

.list {
    height: 126px;
    list-style: none;
    position: absolute;
    right: 10%;
    top: 24%;
    border: none;
    border-radius: 6px;
    backdrop-filter: blur(5px);
    overflow: hidden;
    background: linear-gradient(
            45deg,
            rgba(255, 255, 255, 0.45) 0%,
            rgba(255, 255, 255, 0.85) 100%
    );
    box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.08),
    0 2px 2px rgb(0 0 0 / 3%),
    0 4px 4px rgb(0 0 0 / 4%),
    0 10px 8px rgb(0 0 0 / 5%),
    0 15px 15px rgb(0 0 0 / 6%),
    0 30px 30px rgb(0 0 0 / 7%),
    0 70px 65px rgb(0 0 0 / 9%);
}
.myButton {
    text-align: center;
    border-radius: 4px;
    height: 42px;
    width: 100px;
    padding-top: 10px;
    font-size: 14px;
    border: 0;
    cursor: pointer;
    background-color: rgba(0,0,0,0%);
}
.myButton:hover{
    color: #696bcc;
    background-color: rgba(10, 20, 28, 0.1);
    border: 0;
}
.lines {
    margin-top: 86%;

    height: 2px;
    background-color: rgba(0, 0, 0, 0.1);
    user-select: none;
}

.progress{
    position: absolute;
    margin-top:60%;
    width: 84%;
    padding-left:8%;
    text-align: right;
}

</style>
