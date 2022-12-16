const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port:3000,
    proxy:{
      '/api':{
        target:"http://192.168.0.104:8080",
        ws:true,
        changeOrigin:true
      }
    }
  }
})
