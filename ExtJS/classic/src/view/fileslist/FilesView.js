Ext.define('VPSServer.view.fileslist.FilesView', {
   extend: 'Ext.grid.Panel',

   requires: [
      'VPSServer.model.FileModel',
      'VPSServer.view.fileslist.FilesViewModel',
      'VPSServer.view.fileslist.FilesController',
      'VPSServer.store.FilesStore'
   ],
   controller: 'files',
   viewModel: {
      type: 'filesViewModel'
   },
   /*bind: {
      store: '{viewModelFilesStore}'
   },*/
   store: {
      type: 'filesStore'
   },
   reference: 'filesView', // This config uses the reference to determine the name of the data object to place in the ViewModel

   alias: 'widget.gridfiles',
   title: 'Uploaded files',

   columns: [
      {text: "Name", dataIndex: "name", sortable: true, width: '35%'},
      {text: "Creation date", dataIndex: "creationDate", sortable: true, width: '35%'},
      {text: "Size, MB", dataIndex: "size", sortable: true, width: '19%'},
      {
         menuDisabled: true,
         sortable: false,
         xtype: 'actioncolumn',
         align: 'center',
         width: '5%',
         items: [{
            iconCls: 'pictos pictos-download',
            tooltip: 'Download',
            handler: 'onClickDownload'
         }]
      },
      {
         menuDisabled: true,
         sortable: false,
         xtype: 'actioncolumn',
         align: 'center',
         width: '5%',
         items: [{
            iconCls: 'pictos pictos-delete_black1',
            tooltip: 'Delete',
            handler: 'onClickDelete'
         }]
      }
   ],
   listeners: {
      afterRender: 'onAfterRender'
   }
});