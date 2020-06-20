<template>
  <a-card :bordered="false">
    <div class="page-header-index-wide">
    <div class="amap-wrapper">
      <el-amap vid="amap" :zoom="zoom" :center="center" class="amap-demo">
        <el-amap-polyline :editable="polyline.editable" :path="polyline.path" :events="polyline.events" />
      </el-amap>
    </div>
    <div class="toolbar">
      <button type="button" name="button" @click="changeEditable">change editable</button>
    </div>
  </div>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PipeInfoModal from './modules/PipeInfoModal'

  export default {
    name: "PipeInfoList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      PipeInfoModal
    },
    data () {
      return {
        description: '节点信息管理页面',
        // 表头
        zoom: 12,
          center: [121.5273285, 31.25515044],
          polyline: {
            path: [[121.5389385, 31.21515044], [121.5389385, 31.29615044], [121.5273285, 31.21515044]],
            events: {
              click(e) {
                alert('click polyline');
              },
              end: (e) => {
                let newPath = e.target.getPath().map(point => [point.lng, point.lat]);
                console.log(newPath);
              }
            },
            editable: false
          },
        url: {
          list: "/demo/pipeInfo/list",
          delete: "/demo/pipeInfo/delete",
          deleteBatch: "/demo/pipeInfo/deleteBatch",
          exportXlsUrl: "/demo/pipeInfo/exportXls",
          importExcelUrl: "demo/pipeInfo/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      changeEditable() {
          this.polyline.editable = !this.polyline.editable;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .amap-demo {
        height: 600px;
      }
</style>