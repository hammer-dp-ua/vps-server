Ext.define('VPSServer.view.imagespreview.ImagesPreViewModel', {
   extend: 'Ext.app.ViewModel',

   alias: 'viewmodel.imagespreview',

   data: {
      title: null
   },

   stores: {
      images: {
         type: 'imageFilesStore'
      }
   }
});