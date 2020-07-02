<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :xs="24" :sm="12">
            <a-form-item label="管道设备ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['pipeId']" placeholder="请输入管道设备ID"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="管道描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['pipeDes']" placeholder="请输入管道描述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="首纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hlat']" placeholder="请输入首纬度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="首经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hlong']" placeholder="请输入首经度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="尾纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['rlat']" placeholder="请输入尾纬度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="尾经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['rlong']" placeholder="请输入尾经度" style="width: 100%"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="节点位置信息" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="sensorLocInfoTable.loading"
            :columns="sensorLocInfoTable.columns"
            :dataSource="sensorLocInfoTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'PipeInfoModal',
    mixins: [JEditableTableMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
        },
        refKeys: ['sensorLocInfo', ],
        tableKeys:['sensorLocInfo', ],
        activeKey: 'sensorLocInfo',
        // 节点位置信息
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
            {
              title: '所属管道',
              key: 'pipeId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
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
        url: {
          add: "/pipeinfo/pipeInfo/add",
          edit: "/pipeinfo/pipeInfo/edit",
          sensorLocInfo: {
            list: '/pipeinfo/pipeInfo/querySensorLocInfoByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'pipeId','pipeDes','hlat','hlong','rlat','rlong')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.sensorLocInfo.list, params, this.sensorLocInfoTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          sensorLocInfoList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'pipeId','pipeDes','hlat','hlong','rlat','rlong'))
     },

    }
  }
</script>

<style scoped>
</style>