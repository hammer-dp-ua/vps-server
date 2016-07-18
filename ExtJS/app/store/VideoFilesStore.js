Ext.define('VPSServer.store.VideoFilesStore', {
   extend: 'Ext.data.Store',

   alias: 'store.videoFilesStore',
   model: 'VPSServer.model.FileModel',

   autoSync: true,
   proxy: {
      type: 'rest',
      url: VPSServer.view.main.MainController.VIDEO_FILES_URI,
      reader: {
         type : 'json'
      }
   }
});