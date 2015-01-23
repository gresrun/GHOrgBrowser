/*
bindWithDelay jQuery plugin
Author: Brian Grinstead
MIT license: http://www.opensource.org/licenses/mit-license.php

http://github.com/bgrins/bindWithDelay
http://briangrinstead.com/files/bindWithDelay

Usage:
    See http://api.jquery.com/bind/
    .bindWithDelay( eventType, [ eventData ], handler(eventObject), timeout, throttle )

Examples:
    $("#foo").bindWithDelay("click", function(e) { }, 100);
    $(window).bindWithDelay("resize", { optional: "eventData" }, callback, 1000);
    $(window).bindWithDelay("resize", callback, 1000, true);
*/
(function($) {
$.fn.bindWithDelay = function( type, data, fn, timeout, throttle ) {

    if ( $.isFunction( data ) ) {
        throttle = timeout;
        timeout = fn;
        fn = data;
        data = undefined;
    }

    // Allow delayed function to be removed with fn in unbind function
    fn.guid = fn.guid || ($.guid && $.guid++);

    // Bind each separately so that each element has its own delay
    return this.each(function() {

        var wait = null;

        function cb() {
            var e = $.extend(true, { }, arguments[0]);
            var ctx = this;
            var throttler = function() {
                wait = null;
                fn.apply(ctx, [e]);
            };

            if (!throttle) { clearTimeout(wait); wait = null; }
            if (!wait) { wait = setTimeout(throttler, timeout); }
        }

        cb.guid = fn.guid;

        $(this).bind(type, data, cb);
    });
};
})(jQuery);
(function($) {
	$.fn.createAlert = function(type, message) {
		return this.html('<div class="alert alert-' + type + ' fade in" role="alert">'
				+ '<button type="button" class="close" data-dismiss="alert">'
				+ '<span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' 
				+ message + '</div>');
	};
	$.fn.createErrorAlert = function(message) {
		return this.createAlert('danger', message);
	};
	$.fn.createInfoAlert = function(message) {
		return this.createAlert('info', message);
	};
	$.fn.createWarningAlert = function(message) {
		return this.createAlert('warning', message);
	};
	$.fn.createSuccessAlert = function(message) {
		return this.createAlert('success', message);
	};
})(jQuery);
$(document).ready(function() {
    $('#repoSearchForm').on('submit', function(e) {
        e.preventDefault();
        var valid = true;
        $('#repoSearchForm input').each(function() {
        	var field = $(this);
        	var hasNoContent = (field.val().length == 0);
        	field.parent().toggleClass('has-error', hasNoContent);
        	valid &= !hasNoContent;
        });
        if (valid) {
        	this.submit();
        }
    });
});
