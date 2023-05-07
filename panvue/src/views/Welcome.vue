<template>
    <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
        <div style="flex: 1; ">
            <el-image style="width: 100%;height: 100% " fit="cover"
                      :src="require('../assets/img/bg.jpg')"/>
        </div>
        <div style="width: 25vw; background-color: rgba(240,240,240, 20%);text-align: center; ">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {
    name: "Login"
}
</script>

<style scoped>

</style>