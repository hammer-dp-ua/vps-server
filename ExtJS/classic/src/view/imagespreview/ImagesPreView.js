Ext.define('VPSServer.view.imagespreview.ImagesPreView', {
   extend: 'Ext.panel.Panel',

   requires: [
      'VPSServer.store.ImageFilesStore',
      'VPSServer.view.imagespreview.ImagesPreviewController'
   ],

   alias: 'widget.imagespreview',
   title: 'Image files',
   reference: 'imagesPreView',
   closable: true,

   controller: 'imagespreview',

   items: Ext.create('Ext.view.View', {
      store: {
         type: 'imageFilesStore'
      },

      tpl: [
         '<tpl for=".">',
            '<div class="" id="{name:stripTags}">',
               '<div class=""><img src="{url}" title="{name:htmlEncode}"></div>',
               '<span class="">{shortName:htmlEncode}</span>',
            '</div>',
         '</tpl>',
         '<div class=""></div>'
      ],
      multiSelect: true,
      height: 310,
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.thumb-wrap',
      emptyText: 'No images to display',
      listeners: {
         selectionchange: function (dv, nodes) {
            var l = nodes.length,
               s = l !== 1 ? 's' : '';
            this.up('panel').setTitle('Simple DataView (' + l + ' item' + s + ' selected)');
         }
      }
   }),

   show: function(invokedView, videoFileName) {
      if (!invokedView) {
         throw error("Invoked view is not specified");
      }
      if (!videoFileName) {
         throw error("Video file name is not specified");
      }

      var controller = this.getController();

      controller.setInvokedView(invokedView);
      controller.setVideoFileName(videoFileName);

      this.callParent(); // Call the superclass "show" method
   }
});
