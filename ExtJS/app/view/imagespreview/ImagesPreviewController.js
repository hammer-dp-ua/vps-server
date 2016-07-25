Ext.define('VPSServer.view.imagespreview.ImagesPreviewController', {
   extend: 'Ext.app.ViewController',
   alias: 'controller.imagespreview',

   config: {
      invokedView: null
   },

   loadAndUpdateData: function(invokedView, videoFileName) {
      if (!invokedView) {
         throw error("Invoked view is not specified");
      }
      if (!videoFileName) {
         throw error("Video file name is not specified");
      }

      this.setInvokedView(invokedView);

      var view = this.getView();
      var store = view.getViewModel().getStore('images');
      store.load({
         scope: this,
         callback: function(records, operation, success) {

         }
      }, videoFileName);
      /*if (typeof executeOnLoad === 'function') {
         executeOnLoad.exec();
      }*/
      //view.items.items[0].update(store.getData());
   },

   onClose: function() {
      this.getView().hide();
      this.getInvokedView().show();
   },

   openImage: function() {
      var window = Ext.create('VPSServer.view.fullscreenimage.ImageView');
      window.show();
   }
});