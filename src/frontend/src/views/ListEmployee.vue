<template>
  <main id="list-employee">
    <h1>Список сотрудников</h1>
    <div class="main">
      <div class="home">
        <div class="table" id="customers">
          <thead>
          <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Права доступа</th>
            <th>Компания</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in employee" :key="user.employeeId">
            <td>{{user.name}}</td>
            <td>{{user.lastName}}</td>
            <td>{{user.privileges}}</td>
            <td>{{user.company.name}}</td>
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

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #2d3748;
  color: white;
}
</style>
<script>
export default {
  name: "testNow",
  data(){
    return{
      employee:''
    }
  },
  mounted() {
    fetch("/api/allEmployee")
        .then((response)=>response.json())
        .then((data)=>{
          this.employee=data;
        })
  },
}
</script>