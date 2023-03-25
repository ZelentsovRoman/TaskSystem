<template>
  <main id="add-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
          <h1 class="head">Добавление нового сотрудника</h1>
          <span class="text">Имя</span>
          <input v-model="arremployee.name" class="input" required type="text">
          <span class="text">Фамилия</span>
          <input v-model="arremployee.lastName" class="input" required type="text">
          <span class="text">Права доступа</span>
          <select v-model="arremployee.privileges" class="selector" required>
            <option>User</option>
            <option>Admin</option>
          </select>
          <span class="text">Логин</span>
          <input v-model="user.login" class="input" required type="text">
          <span class="text">Пароль</span>
          <input v-model="user.password" class="input" required type="password">
          <span v-if="text.length>0" class="error">{{ text }}</span>
          <button class="btn" type="submit">Добавить</button>
        </form>
      </div>
    </div>
  </main>
</template>
<style lang="scss" scoped>
@import "./src/views/css/employee.scss";

.error {
  color: red;
  margin-left: 1rem;
  margin-bottom: 5px;
}
</style>
<script>
import router from "@/router";

export default {
  data() {
    return {
      arremployee: {
        name: '',
        lastName: '',
        company: '',
        privileges: ''
      },
      company: {
        name: ''
      },
      user: {
        login: '',
        password: '',
        employee: ''
      },
      text: ''
    }
  },
  methods: {
    async submit(e) {
      e.preventDefault()
      this.user.employee = this.arremployee
      this.arremployee.company = this.company
      var s1 = JSON.stringify(this.user, null, 2)
      var checkUser = await fetch("/api/findUser", {
            body: s1,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      const check = await checkUser.json()
      if (check !== null) {
        if (check.login === this.user.login) {
          this.text = 'Пользователь с таким логином уже существует'
        } else {
          this.save()
        }
      } else this.save()
    },
    async save() {
      this.arremployee.company = JSON.parse(localStorage.getItem('user')).employee.company
      this.arremployee = JSON.stringify(this.arremployee, null, 2)
      var emp = await fetch("/api/saveEmployee", {
            body: this.arremployee,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      this.user.employee = await emp.json()
      this.user = JSON.stringify(this.user, null, 2)
      await fetch("/api/saveUser", {
            body: this.user,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      router.push({path: '/'})
    }
  }
}
</script>

