<template>
  <main id="list-employee">
    <h1>Список сотрудников</h1>
    <div class="main">
      <div class="home">
        <div id="customers" class="table">
          <thead>
          <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Права доступа</th>
            <th>Компания</th>
            <th>Удаление</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in employee" :key="user.employeeId">
            <td>{{ user.name }}</td>
            <td>{{ user.lastName }}</td>
            <td>{{ user.privileges }}</td>
            <td>{{ user.company.name }}</td>
            <button v-if="this.newemp.employeeId!==user.employeeId" class="material-icons" name="delete"
                    @click="deleteUser(user)">clear
            </button>
          </tr>
          </tbody>
        </div>
      </div>
    </div>
  </main>
</template>
<style lang="scss" scoped>
@import "./src/views/css/home.scss";

#customers {
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even) {
  background-color: #f2f2f2;
}

#customers tr:hover {
  background-color: #ddd;
}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #2d3748;
  color: white;
}
button[name='delete'] {
  color: #2d3748;
  position: relative;
  margin-left: 1.5rem;
  width: calc(40px - 2 * 1px);
  height: calc(40px - 2 * 1px);
}
.button{
  margin: 0;
}
</style>
<script>
export default {
  name: "testNow",
  data() {
    return {
      employee: '',
      userId: JSON.parse(localStorage.getItem('user')),
      newemp:JSON.parse(localStorage.getItem('user')).employee
    }
  },
  async mounted() {
    this.userId = JSON.stringify(this.userId, null, 2)
    await fetch("/api/allEmployee", {
          body: this.userId,
          headers: {
            'Content-Type': 'application/json'
          },
          method: "post"
        }
    )
        .then((response) => response.json())
        .then((data) => {
          this.employee = data;
        })
  },
  methods:{
    async deleteUser(user) {
      console.log(user)
      user=JSON.stringify(user,null,2)
      await fetch("/api/deleteUser", {
            body: user,
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
    }
  }
}
</script>