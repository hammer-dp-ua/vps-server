Ext.define('VPSServer.view.fileslist.FilesViewModel', {
   extend: 'Ext.app.ViewModel',
   alias: 'viewmodel.filesViewModel',

   stores: {
      viewModelFilesStore: {
         type: 'filesStore'
      }
   }
});