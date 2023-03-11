<template>
  <main id="reg-user">
    <div class="main">
      <div class="home">
        <form @submit="submit">
          <h1 class="head">Авторизация</h1>
          <span class="text">Логин</span>
          <input v-model=user.login class="input" required type="text">
          <span class="text">Пароль</span>
          <input v-model=user.password class="input" required type="password">
          <span v-if="text.length>0" class="error">{{ text }}</span>
          <button class="btn" type="submit">Войти</button>
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
      user: {
        login: '',
        password: '',
      },
      text: '',
    }
  },
  methods: {
    async submit(e) {
      e.preventDefault()
      var send = JSON.stringify(this.user, null, 2)
      await fetch("/api/auth", {
            body: send,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      ).then(async response => {
            const user = await response.json();
            if (user == 'NOT_FOUND') {
              this.text = 'Неверный логин или пароль'
            } else {
              localStorage.setItem('user', JSON.stringify(user))
              localStorage.setItem('auth', true)
              router.push({path: '/'})
                  .then(() => {
                    this.$router.go()
                  })
            }
          }
      )
    }
  }
}
</script>
