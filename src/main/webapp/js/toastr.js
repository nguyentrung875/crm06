$(document).ready(function() {
      $(".tst1").click(function(){
           $.toast({
            heading: 'Welcome to my Pixel admin',
            text: 'Use the predefined ones, or specify a custom position object.',
            position: 'top-center',
            loaderBg:'#ff6849',
            icon: 'info',
            hideAfter: 3000, 
            stack: 6
          });

     });

      $(".tst2").click(function(){
           $.toast({
            heading: 'Welcome to my Pixel admin',
            text: 'Use the predefined ones, or specify a custom position object.',
            position: 'top-center',
            loaderBg:'#ff6849',
            icon: 'warning',
            hideAfter: 3500, 
            stack: 6
          });

     });
      $(".tst3").click(function(){
           $.toast({
            heading: 'Welcome to my Pixel admin',
            text: 'Use the predefined ones, or specify a custom position object.',
            position: 'top-center',
            loaderBg:'#ff6849',
            icon: 'success',
            hideAfter: 3500, 
            stack: 6
          });

     });

      $(".tst4").click(function(){
           $.toast({
            heading: 'Welcome to my Pixel admin',
            text: 'Use the predefined ones, or specify a custom position object.',
            position: 'top-center',
            loaderBg:'#ff6849',
            icon: 'error',
            hideAfter: 3500
            
          });

     });
     

});
          
