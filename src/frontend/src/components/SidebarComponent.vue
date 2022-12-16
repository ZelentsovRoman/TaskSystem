<template>
  <aside class="is-expanded">
    <div class="logo">
      <span class="material-icons">checklist</span>
      <br>
    </div>
    <div class="menu">
      <router-link to="/" class="button">
        <span class="material-icons">description</span>
        <span class="text">Задачи</span>
      </router-link>
      <router-link to="/addTask" class="button">
        <span class="material-icons">add</span>
        <span class="text">Добавить задачу</span>
      </router-link>
      <router-link to="/listEmployee" class="button">
        <span class="material-icons">people</span>
        <span class="text">Список сотрудников</span>
      </router-link>
    </div>
    <div class="flex"></div>
    <div class="menu">
      <router-link to="/addEmployee" class="button">
        <span class="material-icons">group_add</span>
        <span class="text">Добавление сотрудника</span>
      </router-link>
      <router-link to="/register" class="button">
        <span class="material-icons">person_add</span>
        <span class="text">Регистрация</span>
      </router-link>
      <router-link to="/login" class="button">
        <span class="material-icons">login</span>
        <span class="text">Войти</span>
      </router-link>
      <router-link to="/logout" class="button">
        <span class="material-icons">logout</span>
        <span class="text">Выйти</span>
      </router-link>
    </div>
  </aside>
</template>
<style lang="scss" scoped>
@import "../views/css/sidebar";
</style>
