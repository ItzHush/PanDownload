<template>
    <div class="Files">
        <p style="font-size: 20px; position: absolute; top: 62px;">文件</p>
        <div style="padding-left: 72%">
            <el-dropdown style="display: inline-block; padding-right: 20px">
      <span class="el-dropdown-link">
        排序<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
                <el-dropdown-menu class="menu" slot="dropdown">
                    <el-button class="cusBottom" @click="sortBySizeAsc"><i class="el-icon-top"></i>大小</el-button><br>
                    <el-button class="cusBottom" @click="sortBySizeDesc"><i class="el-icon-bottom"></i>大小</el-button><br>
                    <el-button class="cusBottom" @click="sortByDateAsc"><i class="el-icon-top"></i>日期</el-button><br>
                    <el-button class="cusBottom" @click="sortByDateDesc"><i class="el-icon-bottom"></i>日期</el-button>
                </el-dropdown-menu>
            </el-dropdown>
                    <el-input style="display:inline-block;width: 50%;" placeholder="搜索" suffix-icon="el-icon-research" v-model="searchName"></el-input>
                  <el-button style="display: inline-block; margin-left: 20px" icon="el-icon-search" type="primary" round @click="toSearch">搜索</el-button>

        </div>
<!--        排序方式-->


        <div class="nullshow" v-if="!this.files.length">
            <ul style="list-style-type:none">
                <li @click="uploadFile">
                    <div>上传文件</div>
                    <p><img src="../assets/img/createfolder.png" height="72" width="72"/></p>
                </li>
                <li style="margin-left: 28px;">
                    <div>空空如也</div>
                    <p><img src="../assets/img/uploadfile.png" height="72" width="72" alt=""/></p>
                </li>
                <li style="margin-left: 28px;">
                    <div>欢迎使用</div>
                    <p><img src="@/assets/img/uploadfolder.png" height="72" width="72" alt=""/></p>
                </li>
            </ul>
        </div>

        <el-main v-if="this.files.length">
            <div style="float: left; width: 175px; height: 150px;text-align: center" v-for="item in this.files ">
                <el-dropdown trigger="click" >
                    <el-button class="customButton">
                        <img :src="require(`../assets/img/${item.ext}.png`)" style="width: 106px; " alt=""/><br>
                        <span style="margin-top: 0;font-size: 14px;text-align: center">{{ item.showName }}</span>
                    </el-button>
                    <el-dropdown-menu slot="dropdown" class="menu" v-show="visible">
                        <a :href="'http://182.84.10.33:8181/files/downloadFiles/0/'+ item.id" v-if="!item.isimg">
                            <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px;" @click="download"
                            ><i class="el-icon-download" style="padding-right: 2px;padding-left: -2px"></i> 下载
                            </el-button>
                        </a>
                        <a :href="'http://182.84.10.33:8181/files/downloadFiles/1/'+ item.id" v-if="item.isimg">
                            <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px;" @click="downloadimg"
                            ><i class="el-icon-download" style="padding-right: 2px;padding-left: -2px"></i> 下载
                            </el-button>
                        </a>
                        <br>
                        <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px" @click="collect(item.id)">
                            <i class="el-icon-collection-tag" style="padding-right: 2px;padding-left: -2px"></i> 收藏</el-button>
                        <br>
                        <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px" @click="open(item)">
                            <i class="el-icon-tickets" style="padding-right: 2px;padding-left: -2px"></i> 属性</el-button>
                        <div class="lines"></div>
                        <el-button class="Del" style="border:0; width: 100%; font-size: 16px" @click="deleteById(item.id)">
                            <i class="el-icon-delete" style="padding-right: 2px;padding-left: -2px"></i> 删除</el-button>
                    </el-dropdown-menu>
                </el-dropdown>


            </div>
        </el-main>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: "Files",
    data() {
        return {
            isShow: false,
            visible: false,
            searchName: '',
            files: [{
                showName:'',
                changetime: '',
                downcounts: '',
                ext: '',
                icon: '',
                id: '',
                iscollect: '',
                isimg: '',
                newfilename: '',
                oldfilename: '',
                path: '',
                size: '',
                ordinarySize: '',
                ordinaryUploadTime:'',
                sort: '',
                sortway: '',
                type: '',
                uploadtime: '',
                user_id: ''
            }],
        }
    },
    methods: {
        uploadFile() {
            this.$message.warning("点上面那个，别点我")
        },
        download() {
            this.$message.info("开始下载")
        },
        downloadimg() {
            this.$message.info("开始下载")
        },
        open: function (item){
            const alert_content =  '文件名称：' + item.oldfilename + `<br/>` +
                '文件类型：'+ item.ext + `<br/>` +
                '文件大小：'+item.size+`<br/>` +
                '文件位置：'+item.path+`<br/>`+
                '上传时间：'+item.uploadtime+`<br/>`+
                '下载次数：'+item.downcounts+`<br/>`
            this.$alert(alert_content, '属性', {
                confirmButtonText: '确定',
                dangerouslyUseHTMLString: true
            })
        },
        sortBySizeAsc(){
            this.files.sort(this.asc_size)
        },
        asc_size: function(a, b) {
            return a.ordinarySize - b.ordinarySize
        },
        sortBySizeDesc(){
            this.files.sort(this.desc_size)
        },
        desc_size: function(a, b) {
            return b.ordinarySize - a.ordinarySize
        },
        sortByDateAsc(){
            this.files.sort(this.asc_date)
        },
        asc_date: function (a, b){
            return a.ordinaryUploadTime - b.ordinaryUploadTime
        },
        sortByDateDesc() {
            this.files.sort(this.desc_date)
        },
        desc_date: function (a, b){
            return b.ordinaryUploadTime - a.ordinaryUploadTime
        },
        collect(id){
            axios.post('http://182.84.10.33:8181/files/collect/'+ id).then((resp)=>{
                if(resp.data.success) this.$message.success("已收藏")
            })
        },
        deleteById(id){
            axios.delete('http://182.84.10.33:8181/files/delete/'+ id).then((resp)=>{
                if(resp.data.success){
                    this.$message.info("删除成功。")
                    setTimeout('window.location.reload()', 1000)
                }
            })
        },
        toSearch(){
            if(this.searchName === '' || this.searchName.match(/^\s+$/)){
                window.location.reload()
            }else {
                axios.get('http://182.84.10.33:8181/files/search/' + this.searchName).then((resp) => {
                    if (resp.data.success) {
                        this.files = resp.data.message;
                        for (const file of this.files) {
                            file.ordinaryUploadTime = Date.parse(file.uploadtime)
                            file.uploadtime = new Date(file.ordinaryUploadTime).toLocaleDateString()
                                + "  " + new Date(file.ordinaryUploadTime).toLocaleTimeString()
                            if(file.oldfilename.length > 12){
                                file.showName = file.oldfilename.slice(0, 10)+"..."
                            } else file.showName = file.oldfilename
                            file.ordinarySize = file.size
                            if(file.size < 1024) file.size += 'KB'
                            if(file.size >= 1024){
                                if(file.size < 1024* 1024){
                                    file.size = parseInt(file.size)/ 1024
                                    file.size = Number(file.size).toFixed(1)
                                    file.size += "MB"
                                } else {
                                    file.size = parseInt(file.size)/ 1048576
                                    file.size = Number(file.size).toFixed(1)
                                    file.size += "GB"
                                }
                            }
                        }
                    } else {
                        this.$message.error(resp.data.message)
                    }
                })
            }
        }

    },

    created() {
        axios.get('http://182.84.10.33:8181/files/findallfiles').then((resp) => {
            if (resp.data.success) {
                this.files = resp.data.message;
                for (const file of this.files) {
                    file.ordinaryUploadTime = Date.parse(file.uploadtime)
                    file.uploadtime = new Date(file.ordinaryUploadTime).toLocaleDateString()
                     + "  " + new Date(file.ordinaryUploadTime).toLocaleTimeString()
                    if(file.oldfilename.length > 12){
                        file.showName = file.oldfilename.slice(0, 10)+"..."
                    } else file.showName = file.oldfilename
                    file.ordinarySize = file.size
                    if(file.size < 1024) file.size += 'KB'
                    if(file.size >= 1024){
                        if(file.size < 1024* 1024){
                            file.size = parseInt(file.size)/ 1024
                            file.size = Number(file.size).toFixed(1)
                            file.size += "MB"
                        } else {
                            file.size = parseInt(file.size)/ 1048576
                            file.size = Number(file.size).toFixed(1)
                            file.size += "GB"
                        }
                    }
                }

            }
        })
    }
}
</script>

<style scoped>
@import "@/assets/css/style.css";

.nullshow {
    width: 100%;
    height: 120px;
    bottom: 0;
    top: 0;
    margin-top: 30vh;
    text-align: center;
    cursor: pointer;
}

.nullshow ul {
    margin: 0 auto;
    width: 374px;
    height: 120px;
}

.nullshow ul li {
    float: left;
    width: 106px;
    height: 120px;
    border-radius: 10px;
    background-color: #f5f5f6;
}

.nullshow ul li:hover {
    background-color: #eeeeee;
}

.nullshow ul li p img {
    margin-top: -10px;
}

.nullshow ul li div {
    width: 100%;
    text-align: center;
    font-size: 14px;
    margin-top: 20px;
}

.customButton {
    width: 160px;
    padding-right: 16px;
    padding-left: 16px;
    border: 0;
    background-color: rgba(0,0,0,0%);
}

.menu {
    width: 100px;
    background: linear-gradient(
        45deg,
        rgba(255, 255, 255, 0.3) 0%,
        rgba(255, 255, 255, 0.7) 100%
    );
    border: none;
    border-radius: 6px;
    backdrop-filter: blur(5px);
    padding-bottom: 161px;
    top: 100px;
    left: 100px;
    z-index: 999;
    box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.08),
    0 2px 2px rgb(0 0 0 / 5%),
    0 4px 4px rgb(0 0 0 / 7%),
    0 10px 8px rgb(0 0 0 / 9%),
    0 15px 15px rgb(0 0 0 / 8%),
    0 30px 30px rgb(0 0 0 / 9%),
    0 70px 65px rgb(0 0 0 / 10%);
    transform-origin: top left;
    transform: scale(1);
    height: 0;
    transition: transform .15s ease-in-out,
    height .26s ease-in-out,
    opacity .26s ease-in-out;
}
.cusBut {
    background: rgba(255,255,255,0%);
    height: 40px;
    transition: .3s;
}
.lines {
    height: 1px;
    background-color: rgba(0, 0, 0, 0.1);
    user-select: none;
}
.Del {
    background: rgba(255,255,255,0%);
    height: 40px;
    transition: .3s;
}
.Del:hover {
    background-color: rgb(253,240,240);
    color: rgb(239,110,109);
    transition: .3s;
}

.el-dropdown-link {
    cursor: pointer;
    color: #828CFF;
}
.el-icon-arrow-down {
    font-size: 12px;
}
.demonstration {
    display: block;
    color: #8492a6;
    font-size: 14px;
    margin-bottom: 20px;
}
.cusBottom {
    width: 100%;
    background: rgba(255,255,255,0%);
    height: 40px;
    transition: .3s;
    border: 0;
}

</style>
