<template>
    <div>
        <el-row class="demo-avatar demo-basic">
            <el-col :span="12">
                <div class="demo-basic--circle">
                    <div class="block">
                        <el-avatar shape="square" :size="100"
                                   :src="require('D:/A_panDownload/avatar/'+formLabelAlign.avatar)"></el-avatar>
                    </div>
                    <div class="sub-title">用户头像</div>
                </div>
            </el-col>
        </el-row>
        <el-upload
                class="upload-demo"
                action="http://182.84.10.33:8181/user/avatarUpload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
            <el-button size="small" type="primary">点击更改</el-button>
        </el-upload>
        <div id="perinfo" style="padding-top: 30px">
            <div class="lines"></div>
            <div>
                <span style="width: 200px">昵称：{{ formLabelAlign.nickname }} </span>
                <el-button style="position: absolute; top:298px; left: 500px" type="primary" size="small" plain
                           @click="changeNickname">修改昵称
                </el-button>
            </div>
            <div class="lines"></div>
            <div>用户名：{{ formLabelAlign.username }}</div>
            <div class="lines"></div>

            <div>网盘空间：{{ formLabelAlign.space }}GB</div>
            <div class="lines"></div>
            <div>使用空间：{{ formLabelAlign.usespace }}</div>
            <div class="lines"></div>

            <div>总文件数：{{ formLabelAlign.fileCounts }}</div>
            <div class="lines"></div>

            <div>注册日期：{{ formLabelAlign.regdate }}</div>
            <div class="lines"></div>

            <el-button type="primary" plain @click="changePass">修改密码</el-button>

        </div>
    </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
    name: "userInfo",
    data() {
        return {
            labelPosition: 'right',
            formLabelAlign: {
                username: '',
                nickname: 'Nick',
                password: 'qweqwe',
                space: '默认',
                usespace: '389MB',
                fileCounts: '102',
                regdate: '2002-11-12',
                avatar: '',
                newavatarname: '',
            }
        }
    },
    created() {
        axios.get('http://182.84.10.33:8181/user/getIndexInfo').then((resp) => {
            if (resp.data.success) {
                this.formLabelAlign.avatar = resp.data.message[0];
                this.formLabelAlign.usespace = resp.data.message[1];
                this.formLabelAlign.nickname = resp.data.message[2];
                this.formLabelAlign.space = resp.data.message[3];
                this.formLabelAlign.fileCounts = resp.data.message[5];
                this.formLabelAlign.regdate = resp.data.message[6];
                this.formLabelAlign.newavatarname = resp.data.message[8];
                console.log(this.formLabelAlign);
            } else {
                this.$message.error("后台出问题了，等也没用")
            }
        })
        this.formLabelAlign.username = sessionStorage.getItem("username")
    },
    methods: {
        handleAvatarSuccess() {
            this.$message.success("头像已更改");
            setTimeout("window.location.reload()", "1000")
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
            }
            if (!isLt10M) {
                this.$message.error('上传头像图片大小不能超过 10MB!');
            }
            return isJPG && isLt10M;
        },
        changeNickname() {
            this.$prompt('请输入新昵称', '修改昵称', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({value}) => {
                axios.post('http://182.84.10.33:8181/user/changeNickName/' + value).then((resp) => {
                    if (resp.data.success) {
                        this.$message({
                            type: 'success',
                            message: '你的新昵称是: ' + value
                        });
                        setTimeout("window.location.reload()", "1000")
                    }
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消修改'
                });
            });
        },
        changePass() {
            this.$prompt('请输入新密码', '更改密码', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({value}) => {
                axios.post('http://182.84.10.33:8181/user/changePassword/' + value).then((resp) => {
                    if (resp.data.success) {
                        this.$message({
                            type: 'success',
                            message: '修改成功，请重新登录'
                        });
                        localStorage.clear();
                        sessionStorage.clear();
                        router.push('/')
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消修改'
                });
            });
        },
    }

}
</script>

<style scoped>
.lines {
    width: 300px;
    height: 1px;
    margin-left: 18px;
    margin-bottom: 35px;
    background-color: #eeeded;
}

.avatar-uploader .el-upload {

    border: 10px #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

</style>