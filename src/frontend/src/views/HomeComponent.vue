<template>
  <main id="Home-page">
    <h1>Задачи</h1>
    <div class="main">
      <div class="home">
        <div v-if="tasks.length!==0">
          <div class="home-tab" v-for="item in tasks" :key="item.taskId" @click="clickTask(item.taskId,item)">
            <div class="left">
              <div class="col1">
                <span class="tab-num">#{{item.taskId}}</span>
              </div>
              <div class="col1">
                <span class="tab-desc">Описание:</span>
                <span class="tab-date">Дата создания:</span>
                <span class="tab-status">Статус:</span>
              </div>
              <div class="col2">
                <span class="desc" v-if="item.description===''">Отсутствует</span>
                <span class="desc" v-else>{{ item.description }}</span>
                <span class="date">{{ item.date.substring(0,10) }}</span>
                <span :class="changeColor(item.statusId.status)" name="status">{{ item.statusId.status }}</span>
              </div>
            </div>
            <div class="right">
              <div class="col1">
                <span class="tab-employee">Исполнитель:</span>
                <span class="tab-datestart">Дата начала:</span>
                <span class="tab-dateend">Дата окончания:</span>
              </div>
              <div class="col2">
                <span class="employee">{{ item.employeeId.name }} {{ item.employeeId.lastName }}</span>
                <span class="datestart">{{ item.dateStart }}</span>
                <span class="dateend">{{ item.dateEnd }}</span>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="else">
          <h1>Задачи отстутствуют</h1>
        </div>
      </div>
    </div>
  </main>
</template>
<style lang="scss" scoped>
@import "./src/views/css/home.scss";

.red {
  color: red;
}

.green {
  color: green;
}
.else{
  color: gray;
  margin-top: 6rem;
  h1{
    font-size: 20pt;
  }
}
</style>
<script>
export default {
  name: "testNow",
  data() {
    return {
      tasks: '',
      task:''
    }
  },
  mounted() {
    fetch("/api/allTasks")
        .then((response) => response.json())
        .then((data) => {
          this.tasks = data;
          if(this.tasks.description===null){
            this.tasks.description="Отсутствует"
          }
        })
  },
  methods: {
    changeColor(status) {
      if (status === 'Выполняется') {
        return "red";
      } else return "green"
    },
    clickTask(taskId, item){
      this.$router.push({
        path: "/task/"+item.taskId,
        params:{
          employee: item
        }
      });
    }
  }
}
</script>