Ext.define('VPSServer.store.ImageFilesStore', {
   extend: 'Ext.data.Store',

   alias: 'store.imageFilesStore',
   model: 'VPSServer.model.FileModel',

   autoSync: true,
   proxy: {
      type: 'rest',
      url: '123',
      reader: {
         type: 'json'
      }
   },

   load: function(origParameter, videoFileName) {
      this.proxy.url = Ext.util.Cookies.get(VPSServer.Application.MOCKS_COOKIE) ? 'mocks/' + videoFileName.replace(".ts", "") + '/imageFiles.json' :
            VPSServer.view.main.MainController.IMAGE_FILES_URI + videoFileName.replace(".ts", "");
      this.callParent(origParameter);
   }
});