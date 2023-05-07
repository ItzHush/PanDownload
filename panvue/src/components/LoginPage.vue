<template>
    <div>
        <div style=" margin-top: 150px">
            <div style="font-size: 28px">登录</div>
        </div>
        <div style="margin-top: 30px" id="poster">
            <el-input v-model="loginForm.username" style="width: 75%" icon="el-icon-search" type="text" placeholder="用户名/邮箱">
                <template #prefix><i class="el-icon-user" style="margin-top: 13px; margin-left: 6px"></i></template>
            </el-input>
            <el-input v-model="loginForm.password" style="width: 75%; margin-top: 30px" icon="el-icon-search" type="password" placeholder="密码">
                <template #prefix><i class="el-icon-lock" style="margin-top: 13px; margin-left: 6px"></i></template>
            </el-input>
            <div style="margin-top: 2vh">
                <el-row>
                    <el-col :span="12">
                        <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
                    </el-col>
                    <el-col :span="12">
                        <el-link>忘记密码?</el-link>
                    </el-col>
                </el-row>
            </div>
            <div style="margin-top: 30px">
                <el-button type="primary" round style="width: 40%" @click="login">立即登录</el-button>
            </div>
            <el-divider>
                <div style="color: grey; background-color: rgb(25,25,25);" ></div>
            </el-divider>
            <div style="margin-top: 30px">
                <el-button plain round style="width: 40%" @click="toRegister">注册账号</el-button>
            </div>
        </div>

    </div>
</template>

<script>
import {post} from "@/net";

import {reactive} from "vue";
import router from "@/router";
import axios from "axios";
import Cookies from "js-cookie";

export default {
    data() {
        return {
            loginForm: {
                username: '',
                password: '',
                remember: false
            }
        }
    },
    methods: {

        login() {
            console.log(this.loginForm);
            axios.post('http://182.84.10.33:8181/user/login', this.loginForm).then((resp)=>{
                if(resp.data.success) {
                    if(this.loginForm.remember == true){
                        localStorage.setItem("User", this.loginForm.username)
                    }
                    this.$message.success("登录成功")
                    sessionStorage.setItem('username', this.loginForm.username)
                    router.push('/index')
                } else {
                    this.$message.error("账号或密码错误")
                }
            })
        },
        toRegister(){
            router.push('/register')
        }
    },
    created() {
        if(localStorage.getItem("User")){
            sessionStorage.setItem('username', localStorage.getItem("User"))
            router.push('/index')
        }
    },
}

</script>


<style scoped>

</style>