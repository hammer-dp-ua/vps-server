Ext.define('VPSServer.view.imagespreview.ImagesPreViewModel', {
   extend: 'Ext.app.ViewModel',

   alias: 'viewmodel.imagespreview',

   stores: {
      images: {
         type: 'imageFilesStore'
      }
   }
});