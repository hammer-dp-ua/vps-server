Ext.define('VPSServer.view.fileslist.VideoFilesView', {
   extend: 'Ext.grid.Panel',

   requires: [
      'VPSServer.view.fileslist.VideoFilesViewModel',
      'VPSServer.view.fileslist.VideoFilesController',
      'VPSServer.store.VideoFilesStore'
   ],
   controller: 'files',
   viewModel: {
      type: 'filesViewModel'
   },
   /*bind: {
      store: '{viewModelFilesStore}'
   },*/
   store: {
      type: 'videoFilesStore'
   },
   reference: 'filesView', // This config uses the reference to determine the name of the data object to place in the ViewModel

   alias: 'widget.gridfiles',
   title: 'Uploaded files',

   columns: [
      /*{
         hidden: true,
         text: "Name",
         dataIndex: "name",
         sortable: true,
         width: '35%',
         renderer: function (value) {
            return '<a href="' + VPSServer.view.main.MainController.VIDEO_FILES_URI + value + '" target="_blank">' + value + '</a>';
         }
      },*/
      {
         text: "Preview",
         dataIndex: "name",
         sortable: false,
         width: '25%',
         align: "center",
         menuDisabled: true,
         renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
         return '<img src="' + "images/" +
            value.replace(VPSServer.view.main.MainController.VIDEO_FILES_EXTENSION, "") + "/001" +
            VPSServer.view.main.MainController.IMAGE_FILES_EXTENSION + '" class="first-image-preview" action="openImagesPreview">';
      }
      },
      {text: "Creation date", dataIndex: "creationDate", sortable: true, width: '48%'},
      {text: "Size, MB", dataIndex: "size", sortable: true, width: '15%'},
      /*{
         menuDisabled: true,
         test: "Images",
         sortable: false,
         xtype: 'actioncolumn',
         align: 'center',
         width: '5%',
         items: [{
            iconCls: 'pictos pictos-photos',
            tooltip: 'View images',
            handler: 'onClickOpenImagesPreview'
         }]
      },*/
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
      afterRender: 'onAfterRender',

      itemclick: function(view, record, item, index, event) {
         var action = event.target.getAttribute('action');
         this.getController().actionHandler(action, record.data.name);
      }
   }
});