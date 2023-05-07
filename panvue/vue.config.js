const { defineConfig } = require('@vue/cli-service')
//vue.config.js
module.exports = {
  dev: {
    host: '0.0.0.0'
  }
}

module.exports = {
  // 基本路径 这是最主要的点！！！
  publicPath:  '/',
  // 输出文件目录
  outputDir: 'dist', // 默认dist
  // 指定生成的 index.html 的输出路径 (相对于 outputDir)。也可以是一个绝对路径
  indexPath: 'index.html', // Default: 'index.html'
  devServer: {
    port: 8081 // 此处修改你想要的端口号
  }
}



