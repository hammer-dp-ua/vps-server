Ext.define('VPSServer.store.FilesStore', {
   extend: 'Ext.data.Store',

   alias: 'store.filesStore',
   model: 'VPSServer.model.FileModel',

   autoSync: true,
   proxy: {
      type: 'rest',
      url: VPSServer.view.main.MainController.FILES_URI,
      reader: {
         type : 'json'
      }
   }
});