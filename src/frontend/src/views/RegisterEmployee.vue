<template>
  <main id="add-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
            <h1 class="head">Добавление нового сотрудника</h1>
            <span class="text">Имя</span>
            <input class="input" type="text" required v-model="arremployee.name">
            <span class="text">Фамилия</span>
            <input class="input" type="text" required v-model="arremployee.lastName">
            <span class="text">Права доступа</span>
            <input class="input" type="text" required v-model="arremployee.privileges">
            <span class="text">Логин</span>
            <input class="input" type="text" required v-model="user.login">
            <span class="text">Пароль</span>
            <input class="input" type="password" required v-model="user.password">
            <button class="btn" type="submit">Добавить</button>
        </form>
        </div>
      </div>
  </main>
</template>
<style lang="scss" scoped>
@import "./src/views/css/employee.scss";
</style>
<script>
export default {
  data(){
    return  {
      arremployee:{
        name:'',
        lastName:'',
        company:'',
        privileges: 'admin'
      },
      company:{
        name:''
      },
      user:{
        login:'',
        password:'',
        employee:''
      },
    }
  },
  methods:{
    async submit() {
      fetch("/api/getStatuses")
          .then((response) => response.json())
          .then((data) => {
            this.stat = data;
          })
      this.company=JSON.stringify(this.company,null,2)
      var company = await fetch("/api/saveCompany", {
            body: this.company,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      this.arremployee.company=company
      this.arremployee=JSON.stringify(this.arremployee,null,2)
      var emp = await fetch("/api/saveEmployee", {
            body: this.arremployee,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      this.user.employee=emp
      this.user=JSON.stringify(this.user,null,2)
      await fetch("/api/saveUser", {
            body: this.arremployee,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      this.$router.push("/tasks")
    }
  }
}
</script>

