<template>
  <main id="Home-page">
    <h1>Задачи</h1>
    <div class="main">
      <div class="home">
        <div v-if="tasks.length!==0">
          <div v-for="item in tasks" :key="item.taskId" class="home-tab" @click="clickTask(item.taskId,item)">
            <div class="left">
              <div class="col1">
                <span class="tab-num">#{{ item.taskId }}</span>
              </div>
              <div class="col1">
                <span class="tab-desc">Описание:</span>
                <span class="tab-date">Приоритет</span>
                <span class="tab-status">Статус:</span>
              </div>
              <div class="col2">
                <span v-if="item.description===''" class="desc">Отсутствует</span>
                <span v-else class="desc">{{ item.description }}</span>
                <span :style="{'color': changeColorPriority(item.priority)}" name="status">{{ item.priority }}</span>
                <span :style="{'color': changeColor(item.statusId.status)}" name="status">{{ item.statusId.status }}</span>
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

.else {
  color: gray;
  margin-top: 6rem;

  h1 {
    font-size: 20pt;
  }
}
</style>
<script>
import router from "@/router";

export default {
  name: "testNow",
  data() {
    return {
      tasks: '',
      task: ''
    }
  },
  mounted() {
      fetch("/api/allTasks", {
        body: localStorage.getItem('user'),
        headers: {
          'Content-Type': 'application/json'
        },
        method: "post"
      })
          .then((response) => response.json())
          .then((data) => {
            this.tasks = data;
            if (this.tasks.description === null) {
              this.tasks.description = "Отсутствует"
            }
          })
  },
  methods: {
    changeColor(status) {
      if (status === 'Выполняется') {
        return "red";
      } else return "green"
    },
    changeColorPriority(priority) {
      switch (priority){
        case 'Низкий': return 'green';
        case 'Средний': return 'orange';
        case 'Высокий': return 'red';
      }
    },
    clickTask(taskId, item) {
      router.push({
        path: "/task/" + item.taskId,
        params: {
          employee: item
        }
      });
    }
  }
}
</script>