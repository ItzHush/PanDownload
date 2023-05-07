<template>
    <div>
        <el-container >
            <!-- 侧边栏 -->
            <el-aside width="240px">
                <div class="asideHeader">
                    <div class="logo"><img src="../assets/img/logo.png" alt=""/></div>
                    <div class="name">PanDownload</div>
                </div>
                <aside-bar :asideBarData="asideBarData" ></aside-bar>
                <!--                <user-info-card class="userInfoCard"></user-info-card>-->
            </el-aside>
            <el-container>
                <el-header style="font-size: 18px">
                    <el-button type="primary" round @click="open" icon="el-icon-upload2"
                               class="uploadButton"
                               style="float: left; margin-top: 12px; background-color: rgb(132,139,255);
                                ;border: 0">上传
                    </el-button>
                    <el-dialog
                        :visible.sync="upload.open"
                        width="400px"
                        append-to-body
                    >
                        <el-upload
                            ref="upload"
                            :limit="20"
                            action="http://182.84.10.33:8181/files/uploadFiles"
                            :on-progress="handleFileUploadProgress"
                            :on-success="handleFileSuccess"
                            :on-error="handleFileError"
                            :before-upload="handleBeforeUpload"
                            :on-change="handChange"
                            :on-remove="handRemove"
                            multiple
                            drag
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">
                                将文件拖到此处，或
                                <em>点击上传</em><br>
                                最多上传20个项目，不超过6GB
                            </div>
                        </el-upload>
                        <div slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="submitFileForm">确 定</el-button>
                            <el-button @click="upload.open = false">取 消</el-button>
                        </div>
                    </el-dialog>
                    <el-dropdown style="float: right;">
                        <i class="el-icon-setting" style="font-size: 20px; "></i>
                        <el-dropdown-menu slot="dropdown">
                            <el-button style="border:0; width: 100%" @click="userInfo">个人中心</el-button>
                            <br>
                            <el-button style="border:0; width: 100%" @click="about">关于</el-button>
                            <br>
                            <el-button style="border:0; width: 100%" @click="logout">退出</el-button>
                            <br>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <span style="float: right;margin-right: 15px">{{ showUserName }}</span>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
        <!--        <progress-dialog></progress-dialog>-->
    </div>


</template>

<script>
import router from "@/router";
import AsideBar from "@/asideBar/AsideBar.vue";
import ProgressDialog from "@/progressDialog/ProgressDialog.vue";

export default {
    name: "Index",
    components: {
        AsideBar,
        ProgressDialog
    },
    data() {
        return {
            upload: {
                // 是否显示弹出层
                open: false,
            },
            asideBarData: [
                {
                    path: "/files",
                    name: "文件",
                    icon: "files",
                },
                {path: "/albums", name: "相册", icon: "picture-outline-round"},
                {path: "/collects", name: "收藏夹", icon: "star-off"},
            ],
            downloadFileInfo: {
                name: "",
                url: "",
            },
        };
    },
    computed: {
        showUserName() {
            return sessionStorage.getItem('username')
        }
    },
    methods: {
        open() {
            this.upload.open = true;
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

        handleBeforeUpload(file) {
            // 限制文件上传大小
            const limit = file.size / 1024 / 1024 < 1024;
            if (!limit) {
                this.$message.error("上传文件大小不能超过 1GB!");
                this.upload.isUploading = false;
                return false;
            }
        },

        // 文件上传中处理
        handleFileUploadProgress(event, file, fileList) {
            this.upload.isUploading = true;
        },

        //文件上传错误
        handleFileError() {
            this.$message.error("文件上传失败");
            this.fullScreenLoading?.close();
        },

        //文件变化
        handChange(file, fileList) {
            console.log("文件改变")
        },

        //删除移除
        handRemove(file, fileList) {
            console.log("文件移除")
        },

        // 文件上传成功处理
        handleFileSuccess() {
            this.$message.success("上传成功")
        },

        // 提交上传文件
        submitFileForm() {
            this.$refs.upload.submit();
            window.location.reload()
        }

    }

}
</script>

<style scoped>
.el-aside {
    background-color: #f5f5f6;
    height: 100vh;
    position: sticky;
    top: 0;
    overflow: hidden;
    z-index: 10000;
}

.el-main {
    width: calc(100vw - 240px);
}

.asideHeader {
    display: flex;
    align-items: center;
    margin: 20px;
    position: relative;
}

.asideHeader .logo {
    width: 50px;
}

.asideHeader .logo img {
    width: 100%;
}

.asideHeader .name {
    color: #25262b;
    font-size: 20px;
    font-weight: bold;
    margin-left: 7px;
}

.userInfoCard {
    position: absolute;
    bottom: 0;
}

.el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
}
.uploadButton:hover  {
    font-weight: bolder;
    opacity: 75%;
    transition: .3s;
}


</style>