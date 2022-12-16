import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
	history: createWebHistory(),
	mode: history,
	routes: [
		{
			path: '/',
			component: ()=> import('../views/HomeComponent.vue')
		},
		{
			path: '/addEmployee',
			component: () => import('../views/RegisterEmployee.vue')
		},
		{
			path: '/register',
			component: () => import('../views/RegisterUser.vue')
		},
		{
			path: '/login',
			component: () => import('../views/LoginUser.vue')
		},
		{
			path:'/listEmployee',
			component:() => import('../views/ListEmployee.vue')
		},
		{
			path:'/task/:id',
			component:() => import('../views/TaskComponent.vue')
		},
		{
			path:'/addTask',
			component:() => import('../views/NewTaskComponent.vue')
		}
	],
})

export default router