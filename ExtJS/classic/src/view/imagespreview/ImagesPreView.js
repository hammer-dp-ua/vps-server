Ext.define('VPSServer.view.imagespreview.ImagesPreView', {
   extend: 'Ext.panel.Panel',

   requires: [
      'VPSServer.view.imagespreview.ImagesPreviewController',
      'VPSServer.view.fullscreenimage.ImageView',
      'VPSServer.model.FileModel',
      'VPSServer.store.ImageFilesStore'
   ],

   viewModel: {
      type: 'imagespreview'
   },
   controller: 'imagespreview',

   alias: 'widget.imagespreview',
   reference: 'imagesPreView',
   closable: true,
   closeAction: 'hide',
   scrollable: true,
   frame: true,
   //collapsible: true,

   items: Ext.create('Ext.view.View', {
      bind: {
         store: '{images}'
      },

      tpl: [
         '<tpl for=".">',
            '<div class="preview-image-block" id="{name:stripTags}">',
               '<div class=""><img class="preview-image" src="{uri}" title="{name:htmlEncode}"></div>',
               '<span class="preview-image-name">{name:htmlEncode}</span>',
            '</div>',
         '</tpl>',
         '<div class=""></div>'
      ],

      multiSelect: false,
      overItemCls: 'preview-image-block-over',
      itemSelector: 'div.preview-image-block', // required

      listeners: {
         selectionchange: function(dv, nodes) {
            this.up('panel').getController().openImage(nodes[0].id);
         }
      }
   }),

   show: function(invokedView, videoFileName) {
      this.setTitle(videoFileName);
      this.getController().loadAndUpdateData(invokedView, videoFileName);
      this.callParent(); // Call the superclass "show" method
   },

   listeners: {
      close: 'onClose'
   }
});
