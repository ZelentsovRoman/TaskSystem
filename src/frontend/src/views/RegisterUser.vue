<template>
  <main id="reg-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
          <h1 class="head">Регистрация</h1>
          <span class="text">Имя</span>
          <input v-model="arremployee.name" class="input" required type="text">
          <span class="text">Фамилия</span>
          <input v-model="arremployee.lastName" class="input" required type="text">
          <span class="text">Название компании</span>
          <input v-model="company.name" class="input" required type="text">
          <span class="text">Логин</span>
          <input v-model="user.login" class="input" required type="text">
          <span class="text">Пароль</span>
          <input v-model="user.password" class="input" required type="password">
          <span v-if="text.length>0" class="error">{{ text }}</span>
          <button class="btn" type="submit">Зарегистрировать</button>
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
        privileges: 'Admin'
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
      this.text = ''
      var comp = await fetch("/api/getCompany", {
            body: JSON.stringify(this.company, null, 2),
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      this.arremployee.company = await comp.json()
      this.user.employee = this.arremployee
      var log = await fetch("/api/findUser", {
            body: JSON.stringify(this.user, null, 2),
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
      const checkUser = await log.json()
      if (this.arremployee.company !== null) {
        if (this.arremployee.company.name === this.company.name) {
          this.text = 'Такая компания уже существует'
        } else {
          if (checkUser !== null) {
            if (checkUser.login === this.user.login) {
              this.text = 'Пользователь с таким логином уже существует'
            } else this.save()
          } else this.save()
        }
      } else {
        if (checkUser !== null) {
          if (checkUser.login === this.user.login) {
            this.text = 'Пользователь с таким логином уже существует'
          } else this.save()
        } else this.save()
      }
    },
    async save() {
      localStorage.clear()
      router.push({path: '/'})
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
                })
          })
      location.reload()
    }
  }
}
</script>
