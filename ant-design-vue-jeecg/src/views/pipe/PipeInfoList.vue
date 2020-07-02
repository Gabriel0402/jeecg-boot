<template>
  <a-card :bordered="false">
    <div class="page-header-index-wide">
    <div class="amap-wrapper">
      <el-amap vid="amap" :zoom="zoom" :center="center" :zoomEnable="false" class="amap-demo">
        <el-amap-polyline v-for="(line, lindex) in lines" :key='lindex' :path="line" />
        <el-amap-marker v-for="(marker, mindex) in markers" :key='marker.id' :position="marker.position" :events="marker.events" :visible="marker.visible" :draggable="marker.draggable" :vid="mindex"></el-amap-marker>
      </el-amap>
    </div>
  </div>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PipeInfoModal from './modules/PipeInfoModal'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction } from '@/api/manage'
  import Vue from 'vue'
  import { filterObj } from '@/utils/util';
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'

  export default {
    name: "PipeInfoList",
    mixins:[mixinDevice],
    components: {
      PipeInfoModal
    },
    data () {
      return {
        description: '节点信息管理页面',
        // 表头
        zoom: 18,
        center: [119.953, 31.688],
        lines:[],
        markers: [],
        url: {
          list: "/pipeinfo/pipeInfo/list",
          slist:'/pipeinfo/pipeInfo/querySensorLocInfoByMainId'
        },
        dictOptions:{},
        // data related
        superQueryParams: '',
        /** 高级查询拼接方式 */
        superQueryMatchType: 'and',
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
        queryParam: {},
        /* 数据源 */
        dataSource:[],
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'管道设备ID',
            align:"center",
            dataIndex: 'pipeId'
          },
          {
            title:'管道描述',
            align:"center",
            dataIndex: 'pipeDes'
          },
          {
            title:'首纬度',
            align:"center",
            dataIndex: 'hlat'
          },
          {
            title:'首经度',
            align:"center",
            dataIndex: 'hlong'
          },
          {
            title:'尾纬度',
            align:"center",
            dataIndex: 'rlat'
          },
          {
            title:'尾经度',
            align:"center",
            dataIndex: 'rlong'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        sensorLocInfoTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '设备号',
              key: 'uid',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '创建人',
              key: 'createBy',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '纬度',
              key: 'latitude',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '经度',
              key: 'longitude',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            // {
            //   title: '所属管道',
            //   key: 'pipeId',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            {
              title: '节点描述',
              key: 'desp',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    created() {
      this.loadData();
    },
    methods: {
      loadData(arg){
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            let ids=this.dataSource.map((item)=>{
              return item.id;
            })
            // this.markers= 
            this.lines = this.dataSource.map((item)=>{
              return [[item.hlong,item.hlat],[item.rlong,item.rlat]]
            })
            for (let id of ids) {
              let params = { id: id }
              getAction(this.url.slist,params).then(res=>{
                let { result } = res
                let dataSource = []
                if (result) {
                  if (Array.isArray(result)) {
                    dataSource = result
                  } else if (Array.isArray(result.records)) {
                    dataSource = result.records
                  }
                }
                dataSource.forEach((item)=>{
                  let marker = {
                    position: [item.longitude, item.latitude],
                    id:item.id
                  };
                  this.markers.push(marker);
                })
              
              })
            }
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      getQueryParams() {
      //获取查询条件
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
          sqp['superQueryMatchType'] = this.superQueryMatchType
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        //TODO 字段权限控制
        var str = "id,";
        this.columns.forEach(function (value) {
          str += "," + value.dataIndex;
        });
        return str;
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