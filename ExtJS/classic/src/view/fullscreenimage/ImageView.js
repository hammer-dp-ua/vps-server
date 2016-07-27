Ext.define('VPSServer.view.fullscreenimage.ImageView', {
   extend: 'Ext.window.Window',

   requires: [
      'VPSServer.view.fullscreenimage.ImageController'
   ],

   controller: 'fullScreenImage',

   layout: 'fit',
   modal: true,
   width: '100%',
   height: '100%',
   closeToolText: null,

   config: {
      imagesStore: null
   },

   items: [{
      xtype: 'container',
      reference: 'image',
      style: {
         'background-position': 'center',
         'background-repeat': 'no-repeat',
         'background-size': 'contain'
         //'background-image': 'url("mocks/video_file_name/001.jpeg")'
      }
   }, {
      xtype: 'button',
      text: '<',
      cls: 'button-prev-image',
      focusCls: '',
      handler: 'viewPrevImage'
   }, {
      xtype: 'button',
      text: '>',
      cls: 'button-next-image',
      focusCls: '',
      handler: 'viewNextImage'
   }],

   setImageName: function(imageName) {
      if (!this.getImagesStore()) {
         throw "Store hasn't been set yet";
      }
      this.getController().updateImage(imageName);
   }
});