import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    mode: history,
    routes: [
        {
            path: '/',
            name: 'Home',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/HomeComponent.vue')
        },
        {
            path: '/addEmployee',
            name: 'AddEmployee',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/RegisterEmployee.vue')
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import('../views/RegisterUser.vue')
        },
        {
            path: '/login',
            name: 'Login',
            meta: {
                requiresAuth: false,
            },
            component: () => import('../views/LoginUser.vue')
        },
        {
            path: '/listEmployee',
            name: 'ListOfEmployee',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/ListEmployee.vue')
        },
        {
            path: '/task/:id',
            name: 'Task',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/TaskComponent.vue')
        },
        {
            path: '/addTask',
            name: 'AddTask',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/NewTaskComponent.vue')
        },
        {
            path: '/profile',
            name: 'Profile',
            meta: {
                requiresAuth: false,
            },
            beforeEnter: (to, from, next) => {
                if (!localStorage.getItem('auth') && to.name !== 'Login') next({
                    name: 'Login'
                })
                else next()
            },
            component: () => import('../views/ProfileComponent.vue')
        }
    ],
})
router.beforeEach(async (to, from, next) => {
    var user = localStorage.getItem('user')
    if (user !== null) {
        await fetch("/api/update", {
                body: user,
                headers: {
                    'Content-Type': 'application/json'
                },
                method: "post"
            }
        ).then(async response => {
            const str = JSON.stringify(await response.json())
            if(response.status==404){
                localStorage.clear()
                next({name:'Login'})
                location.reload()
            }else localStorage.setItem('user', str)
        })
    }
    next()
})
export default router