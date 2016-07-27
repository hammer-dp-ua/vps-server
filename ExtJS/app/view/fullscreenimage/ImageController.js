Ext.define('VPSServer.view.fullscreenimage.ImageController', {
   extend: 'Ext.app.ViewController',
   alias: 'controller.fullScreenImage',

   viewPrevImage: function() {
      var view = this.getView();
      var store = view.getImagesStore();

      var currentImageName = view.getTitle();
      var currentImageIndex = store.find("name", currentImageName);

      if (currentImageIndex > 0) {
         var newImageName = store.getAt(currentImageIndex - 1).data.name;
         this.updateImage(newImageName);
      }
   },

   viewNextImage: function() {
      var view = this.getView();
      var store = view.getImagesStore();

      var currentImageName = view.getTitle();
      var currentImageIndex = store.find("name", currentImageName);

      if (currentImageIndex < store.getData().length - 1) {
         var newImageName = store.getAt(currentImageIndex + 1).data.name;
         this.updateImage(newImageName);
      }
   },

   updateImage: function(imageName) {
      var view = this.getView();
      var imageItem = this.lookupReference('image');
      var store = view.getImagesStore();
      var currentImageIndex = store.find("name", imageName);
      var imageUri = store.getAt(currentImageIndex).data.uri;

      view.setTitle(imageName);
      imageItem.setStyle('background-image', 'url("' + imageUri + '")');
   }
});