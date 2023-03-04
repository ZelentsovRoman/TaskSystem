<template>
  <main id="profile-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
          <h1 class="head">Профиль</h1>
          <span class="text">Имя</span>
          <input v-model=user.employee.name class="input" required type="text">
          <span class="text">Фамилия</span>
          <input v-model=user.employee.lastName class="input" required type="text">
          <span class="text">Логин</span>
          <input v-model=user.login class="input" required type="text">
          <span class="text">Пароль</span>
          <input v-model=user.password class="input" required type="password">
          <span v-if="text.length>0" class="error">{{ text }}</span>
          <button class="btn" type="submit">Изменить данные</button>
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

console.log()
export default {
  data() {
    return {
      showPassword: false,
      user: JSON.parse(localStorage.getItem('user')),
      text: '',
    }
  },
  methods: {
    async submit(e) {
      e.preventDefault()
      if (this.user.login !== JSON.parse(localStorage.getItem('user')).login) {
        var send = JSON.stringify(this.user, null, 2)
        var checkUser = await fetch("/api/findUser", {
              body: send,
              headers: {
                'Content-Type': 'application/json'
              },
              method: "post"
            }
        )
        const check = await checkUser.json()
        if (check !== null) {
          if (check.login === this.user.login) {
            this.text = 'Пользователь с таим логином уже существует'
          } else {
            this.save()
          }
        } else this.save()
      } else this.save()
    },
    async save() {
      router.push({path: '/'})
      var send = JSON.stringify(this.user, null, 2)
      await fetch("/api/EditUser", {
            body: send,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      ).then(async response => {
        if (response.ok) {
          location.reload()
        }
      })
    },
  },
}
</script>
