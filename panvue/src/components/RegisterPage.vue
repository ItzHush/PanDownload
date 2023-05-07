<template>
    <div>
        <div style=" margin-top: 150px">
            <div style="font-size: 28px">注册</div>
        </div>
        <el-form style="text-align: center; margin-top: 30px; width: 74%; margin-left: 13%" :model="ruleForm"
                 status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item prop="username">
                <el-input v-model="ruleForm.username" prefix-icon="el-icon-user-solid"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="nickname">
                <el-input v-model="ruleForm.nickname" prefix-icon="el-icon-user" placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="ruleForm.password" prefix-icon="el-icon-lock" autocomplete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" prefix-icon="el-icon-lock" autocomplete="off"
                          placeholder="请确认密码"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" round style="width: 30%" @click="Register('ruleForm')">注册</el-button>
                <el-button @click="toLogin" round style="width: 30%">去登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import router from "@/router";
import axios from "axios";

export default {
    name: "RegisterPage",
    data() {
        var checkName = (rule, value, callback) => {
            var exist = 1;
            if (!value) {
                return callback(new Error('名称不能为空'));
            } else {
                axios.post('http://182.84.10.33:8181/user/getUserName', this.ruleForm).then((resp) => {
                    if (resp.data.status == 403) {
                        callback(new Error('名称已存在'))
                    } else callback()
                })
            }
        };
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                password: '',
                checkPass: '',
                username: '',
                nickname: ''
            },
            rules: {
                password: [
                    {validator: validatePass, trigger: 'blur'}
                ],
                checkPass: [
                    {validator: validatePass2, trigger: 'blur'}
                ],
                username: [
                    {validator: checkName, trigger: 'blur'},
                    {min: 2, max: 9, message: '长度2-9个字符之间'}
                ]
            }
        };
    },
    methods: {
        Register(ruleForm) {
            var flag = true;
            this.$refs[ruleForm].validate((valid) => {
                if (!valid) {
                    flag = false;
                } else{
                    axios.post('http://182.84.10.33:8181/user/register', this.ruleForm).then((resp) => {
                        if (resp.data.status == 403) {
                            this.$message(new Error('注册失败...'))
                        } else if(resp.data.success){
                            this.$message.success("注册成功，正在跳转登录页")
                            router.push('/')
                        }
                    })
                }
            });
        },
        toLogin() {
            router.push('/')
        }
    }
}
</script>

<style scoped>

</style>