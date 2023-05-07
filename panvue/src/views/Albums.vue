<template>
  <div class="Album">
      <p style="font-size: 20px; position: absolute; top: 62px;">相册</p>
      <el-main>
          <div v-for="(v, date) in this.allImages" v-if="isShow" >
              <p style="font-size: 20px; padding-top: 20px">{{date}}</p>
              <div v-for="img in v.slice().reverse()" style="display: inline-block">
                  <el-dropdown trigger="click" >
                      <el-button class="button">
                          <img :src="require('D:/A_panDownload/files/images/zipped/' + img.newfilename)" style="width: 146px;height: 146px
                           ;padding-top: -10px" alt="">
                      </el-button>
                      <el-dropdown-menu slot="dropdown" class="menu" v-show="visible">
                          <a :href="'http://182.84.10.33:8181/files/downloadFiles/1/'+ img.id">
                              <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px;" @click=""
                              ><i class="el-icon-download" style="padding-right: 2px;padding-left: -2px"></i> 下载
                              </el-button>
                          </a>
                          <br>
                          <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px" @click="collect(img.id)">
                              <i class="el-icon-collection-tag" style="padding-right: 2px;padding-left: -2px"></i> 收藏</el-button>
                          <br>
                          <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px" @click="open(img)">
                              <i class="el-icon-tickets" style="padding-right: 2px;padding-left: -2px"></i> 属性</el-button>
                          <br>
                          <a :href="require('D:/A_panDownload/files/images/' + img.newfilename)" target="_blank">
                              <el-button class="cusBut" style="border:0; width: 100%; font-size: 16px" @click="">
                                  <i class="el-icon-top-right" style="padding-right: 2px;padding-left: -2px"></i> 打开</el-button>
                          </a>
                          <div class="lines"></div>
                          <el-button class="Del" style="border:0; width: 100%; font-size: 16px" @click="deleteById(img.id)">
                              <i class="el-icon-delete" style="padding-right: 2px;padding-left: -2px"></i> 删除</el-button>
                      </el-dropdown-menu>
                  </el-dropdown>


              </div>

          </div>
      </el-main>
  </div>
</template>

<script>

import axios from "axios";
import {toArray} from "core-js/internals/async-iterator-iteration";
import {keys} from "core-js/stable/dom-collections";

export default {
    name: "Album",
    data() {
        return {
            allImages: [{
            }],
            isShow: false
        }
    },
    methods: {
        open: function (item){
            item.ordinaryUploadTime = Date.parse(item.uploadtime)
            item.uploadtime = new Date(item.ordinaryUploadTime).toLocaleDateString()
                + "  " + new Date(item.ordinaryUploadTime).toLocaleTimeString()
            if (item.size < 1024) item.size += 'KB'
            if (item.size >= 1024) {
                if (item.size < 1024 * 1024) {
                    item.size = parseInt(item.size) / 1024
                    item.size = Number(item.size).toFixed(1)
                    item.size += "MB"
                }
            }
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
        asc: function (a, b) {
            return a.ordinaryUploadTime - b.ordinaryUploadTime
        }
    },
    created() {
        axios.get('http://182.84.10.33:8181/files/findAllImages').then((resp)=> {
            if(resp.data.success){
                this.isShow = true
                this.allImages =resp.data.message
            }else this.$message.warning("暂无数据")
        })
    }
}
</script>

<style scoped>
.button{
    border: 0;
    padding: 4px;
}
/*图片预览 缩略图*/
.preview figure {
    float: left;
    width: 30%;
    height:calc(30vw - 0px);
    margin: 1.5%;
}

.preview figure img {
    width: 100%;
}
.menu {
    width: 100px;
    background: linear-gradient(
            45deg,
            rgba(255, 255, 255, 0.36) 0%,
            rgba(255, 255, 255, 0.78) 100%
    );
    border: none;
    border-radius: 6px;
    backdrop-filter: blur(5px);
    padding-bottom: 201px;
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
.Album{
    width: 100%;
    overflow: hidden;
}

</style>
