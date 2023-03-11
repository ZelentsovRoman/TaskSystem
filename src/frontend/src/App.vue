<template>
  <link href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic" rel="stylesheet">
  <div class="app">
    <!-- Sidebar -->
    <Sidebar/>

    <!-- Content -->
    <router-view/>
  </div>
</template>

<script setup>
import Sidebar from './components/SidebarComponent.vue'
</script>

<style lang="scss">
:root {
  --primary: #ffffff;
  --primary-alt: #ffffff;
  --grey: #ffffff;
  --dark: #2d3748;
  --dark-alt: rgb(96, 125, 139);
  --light: #f0f0f0;
  --sidebar-width: 235px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background: var(--light);
  font-family: "Fira sans", sans-serif;
}

button {
  cursor: pointer;
  appearance: none;
  border: none;
  outline: none;
  background: none;
  margin: 15px;
}

.app {
  display: flex;

  main {
    flex: 1 1 0;
    padding: 2rem;
    margin-left: 15rem;

    @media (max-width: 1024px) {
      padding-left: 6rem;
    }
  }
}
</style>
