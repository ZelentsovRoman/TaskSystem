<template>
  <main id="reg-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
          <h1 class="head">Регистрация</h1>
          <span class="text">Имя</span>
          <input class="input" type="text" v-model="arremployee.name" required>
          <span class="text">Фамилия</span>
          <input class="input" type="text" v-model="arremployee.lastName" required>
          <span class="text">Название компании</span>
          <input class="input" type="text" v-model="company.name" required>
          <span class="text">Логин</span>
          <input class="input" type="text" v-model="user.login" required>
          <span class="text">Пароль</span>
          <input class="input" type="password" v-model="user.password" required>
          <button class="btn" type="submit">Зарегистрировать</button>
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
  data() {
    return {
      arremployee: {
        name: '',
        lastName: '',
        company: '',
        privileges: 'admin'
      },
      company: {
        name: ''
      },
      user: {
        login: '',
        password: '',
        employee: ''
      },
    }
  },
  methods: {
    async submit() {
      this.$router.push({ path: '/' })
      fetch("/api/getStatuses")
          .then((response) => response.json())
          .then(async (data) => {
            this.stat = data;
            this.company = JSON.stringify(this.company, null, 2)
            await fetch("/api/saveCompany", {
              body: this.company,
              headers: {
                'Content-Type': 'application/json'
              },
              method: "post"
            })
                .then((response) => response.json())
                .then(async (data) => {
                  this.arremployee.company = data;
                  this.arremployee = JSON.stringify(this.arremployee, null, 2)
                  await fetch("/api/saveEmployee", {
                        body: this.arremployee,
                        headers: {
                          'Content-Type': 'application/json'
                        },
                        method: "post"
                      }
                  ).then((response) => response.json())
                      .then(async (data) => {
                            this.user.employee = data;
                            this.user = JSON.stringify(this.user, null, 2)
                            await fetch("/api/saveUser", {
                                  body: this.user,
                                  headers: {
                                    'Content-Type': 'application/json'
                                  },
                                  method: "post"
                                }
                            )
                          }
                      )
                })
          })
    }
  }
}
</script>
