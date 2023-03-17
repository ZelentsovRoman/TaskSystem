<template>
  <aside class="is-expanded">
    <div class="logo">
      <span class="material-icons">checklist</span>
      <h1 class="title">TaskSystem</h1>
      <br>
    </div>
    <div class="menu">
      <router-link v-if="auth" class="button" to="/">
        <span class="material-icons">description</span>
        <span class="text">Задачи</span>
      </router-link>
      <router-link v-if="auth&&(priveleges==='Admin')" class="button" to="/addTask">
        <span class="material-icons">add</span>
        <span class="text">Добавить задачу</span>
      </router-link>
      <router-link v-if="auth&&(priveleges==='Admin')" class="button" to="/listEmployee">
        <span class="material-icons">people</span>
        <span class="text">Список сотрудников</span>
      </router-link>
    </div>
    <div class="flex"></div>
    <div class="menu">
      <router-link v-if="auth&&(priveleges==='Admin')" class="button" to="/addEmployee">
        <span class="material-icons">group_add</span>
        <span class="text">Добавление сотрудника</span>
      </router-link>
      <router-link class="button" to="/register">
        <span class="material-icons">person_add</span>
        <span class="text">Регистрация</span>
      </router-link>
      <router-link v-if="auth" class="button" to="/profile">
        <span class="material-icons">person</span>
        <span class="text">Профиль</span>
      </router-link>
      <router-link v-if="!auth" class="button" to="/login">
        <span class="material-icons">login</span>
        <span class="text">Авторизация</span>
      </router-link>
      <div v-if="auth" class="button" @click="logout">
        <span class="material-icons">logout</span>
        <span class="text">Выйти</span>
      </div>
    </div>
  </aside>
</template>
<style lang="scss" scoped>
@import "../views/css/sidebar";

.button {
  cursor: pointer;
}
</style>
<script>
export default {
  data() {
    return {}
  },
  methods: {
    logout() {
      this.$router.push({path: '/login'})
      localStorage.clear()
      location.reload()
    },
  },
  computed: {
    priveleges: function () {
      return JSON.parse(localStorage.getItem('user')).employee.privileges
    },
    auth: function () {
      return localStorage.getItem('auth')
    },
  }
}
</script>
