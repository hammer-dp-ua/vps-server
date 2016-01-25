Ext.define('VPSServer.store.FilesStore', {
   extend: 'Ext.data.Store',

   alias: 'store.filesStore',
   model: 'VPSServer.model.FileModel',

   proxy: {
      type: 'rest',
      api: {
         read: 'mocks/files.json',
         destroy: '/video/rest/files'
      },
      //url: '/video/rest/files',
      reader: {
         type : 'json'
      }
   }
});