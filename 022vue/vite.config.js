import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {defineConfig} from 'vite'
import AutoImport from "unplugin-auto-import/vite" //自动导入composition api 和 生成全局typescript说明
import Components from 'unplugin-vue-components/vite' //一款自动引入插件，可以省去比如 UI 库的大量 import 语句。
import VueSetupExtend from 'vite-plugin-vue-setup-extend'  //setup 语法糖 自定义组件 name


const baseApi = 'http://127.0.0.1:8083/products'   //////★★★★★★★★★★★★★这里替换后端地址
const outputDir = 'build_out'//打包后的输出目录
const path = require('path')
const titleName = '网站'  //任务栏网站名称

export default ({mode, command}) => {
    // console.log(command)

    //console.log(env)
    //console.log(env.VITE_APP_BASE_API)
    return defineConfig({

        base: command === 'serve' ? './' : '/',
        define: {
            'process.platform': null,
            'process.version': null,
            'process.env': {
                VUE_APP_BASE_API: baseApi,
                title: titleName
            }
        },
        clearScreen: true,// 设为false 可以避免 vite 清屏而错过在终端中打印某些关键信息
        // 开发服务器选项 https://cn.vitejs.dev/config/#server-options
        server: {
            hmr: {
                overlay: false
            }, // 禁用或配置 HMR 连接 设置 server.hmr.overlay 为 false 可以禁用服务器错误遮罩层
            // 服务配置
            port: 8889, // 类型： number 指定服务器端口;
            open: true, // 类型： boolean | string在服务器启动时自动在浏览器中打开应用程序；
            cors: false // 类型： boolean | CorsOptions 为开发服务器配置 CORS。默认启用并允许任何源
            // proxy: {
            //   // 类型： Record<string, string | ProxyOp 为开发服务器配置自定义代理规则
            //   '/scala-ms': {
            //     target: 'http://xxxx:15980/',
            //     changeOrigin: true,
            //     secure: false
            //   }
            // }
        },
        plugins: [
            vue(),
            vueJsx(),

            AutoImport({
                imports: [
                    "vue",
                    "vue-router",
                    {
                        'vue': [
                            'defineProps',
                            'defineEmits',
                            'defineExpose',
                            'withDefaults'
                        ]
                    },

                ], // 自动导入vue和vue-router相关函数
                dts: "src/auto-import.d.ts" // 生成 `auto-import.d.ts` 全局声明
            }),
            Components({
                dirs: ['src/components'], // 配置需要默认导入的自定义组件文件夹，该文件夹下的所有组件都会自动 import
                include: [/\.vue$/, /\.vue\?vue/, /\.jsx$/],
                dts: "src/components.d.ts" // 生成 `auto-import.d.ts` 全局声明

            }),
            VueSetupExtend(),
            // BUG https://github.com/antfu/unplugin-vue-components/issues/361
            {
                name: 'dev-auto-import-element-plus',
                transform(code, id) {
                    if (command === 'serve' && /src\/main.js$/.test(id)) {
                        return {
                            code: code.replace('/* importElementPlusPlaceholder */', `
                                import ElementPlus from 'element-plus';
                                import 'element-plus/dist/index.css';
                                app.use(ElementPlus);
                            `),
                            map: null
                        }
                    }
                }
            }
        ],
        // 构建选项 https://cn.vitejs.dev/config/#server-fsserve-root
        build: {
            outDir: outputDir,//输出目录
            emptyOutDir: true,
            minify: 'terser',
            brotliSize: false,
            // 消除打包大小超过500kb警告
            chunkSizeWarningLimit: 2000,
            terserOptions: {
                compress: {
                    // 生产环境移除console
                    drop_console: false,
                    drop_debugger: false
                }
            },
            assetsDir: 'static',
            rollupOptions: {
                // 打包将 js 和 css 文件夹分离
                output: {
                    chunkFileNames: 'static/js/[name]-[hash].js',
                    entryFileNames: 'static/js/[name]-[hash].js',
                    assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
                }
            }
        },
        optimizeDeps: {
            include: [
                'element-plus/lib/locale/lang/zh-cn', "@vueuse/core",

            ]
        },
        resolve: {
            alias: {
                '~': path.resolve(__dirname, './'),
                '@': path.resolve(__dirname, 'src')
            },
            extensions: ['.js', '.ts', '.jsx', '.tsx', '.json', '.vue', '.mjs']
        },

    })
}
