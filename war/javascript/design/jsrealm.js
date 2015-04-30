/*!
 * Copyright (c) 2009 Andreas Blixt <andreas@blixt.org>
 * MIT License: http://www.opensource.org/licenses/mit-license.php
 * 
 * JS Realm application
 */

// Extend jQuery functionality to support prettify as a prettify() method.
jQuery.fn.prettify = function () { this.html(prettyPrintOne(this.html())); };

// Put all JS Realm functionality in a closure.
var JsRealm = function ($) {
    var
    // The title of the application.
    appTitle = 'Blixt\'s JavaScript realm',
    // The path to the Two Cents API.
    twoCentsPath = '/js/two-cents.php/',
    
    // Page elements.
    homePage = $('#home'),
    projectPage = $('#project'),
    notFoundPage = $('#not-found'),
    notFoundPath = $('#not-found-path'),
    twoCentsList = $('#two-cents'),
    world = $('#world'),

    JsRealm = arguments.callee,
    included = ['/js/Hash.js', '/js/jquery.hash.js', '/js/Application.js',
                '/js/JSON.js', '/js/Physics.js', '/js/ServiceClient.js',
                '/js/prettify.js', '/js/jsrealm.js'],
    
    // Client for the Two Cents service (supports simple comments on pages.)
    TwoCentsClient = (function () {
        var
        form = twoCentsList.find('#two-cents-new'),
        name = form.find('#two-cents-name'),
        message = form.find('#two-cents-message'),
        submit = form.find('#two-cents-submit'),
        fields = form.find('input:text, textarea'),
        curPath = '',
        
        // Constructor.
        cls = function (path) {
            // Inherit functionality from ServiceClient.
            ServiceClient.call(this, path);
        };

        // Shared public members.
        cls.prototype = {
            get: function (path) {
                curPath = path;
                this.call('get', {path: path}, this.refresh, this);
            },

            put: function (path, name, message) {
                this.call('put', {path: path, name: name, message: message},
                          this.refresh, this);
            },
            
            refresh: function (data) {
                var entry, i, l;

                // Reset list.
                form.nextAll().remove();

                // Reset form.
                message.val(message.data('empty-text'));
                message.addClass('empty');
                submit.attr('disabled', false);

                // Create boxes for existing messages.
                for (i = 0, l = data.length; i < l; i++) {
                    entry = data[i];
                    twoCentsList.append(
                        $('<li/>').append(
                            $('<strong/>').text(entry.name),
                            $('<span/>').text(entry.message)
                        )
                    );
                }
            }
        };

        // Make the fields show their initial value as a field description which
        // is hidden when the field gets focus.
        fields.each(function () {
            var el = $(this);
            el.data('empty-text', el.val());
            el.addClass('empty');
        }).focus(function () {
            var el = $(this);
            if (el.hasClass('empty')) {
                el.val('');
                el.removeClass('empty');
            }
        }).blur(function () {
            var el = $(this);
            if (!el.val()) {
                el.val(el.data('empty-text'));
                el.addClass('empty');
            }
        });

        // Enable submission of entries.
        submit.click(function () {
            if (fields.hasClass('empty')) {
                alert('You must fill in all fields!');
                return;
            }
            submit.attr('disabled', true);
            twoCents.put(curPath, name.val(), message.val());
        });

        return cls;
    })(),
    
    twoCents = new TwoCentsClient(twoCentsPath),
    
    // Helper class for showing one element from a set of elements at a time.
    // All arguments are assumed to be jQuery selections of a single element.
    Switcher = function () {
        // Show the first element, hide the rest.
        var shown = arguments[0], i;
        for (i = 1; i < arguments.length; i++) {
            arguments[i].remove();
        }

        this.show = function (which) {
            if (shown == which) return;
            shown.replaceWith(which);
            shown = which;
        };
    },
    
    // Switcher for the pages.
    pages = new Switcher(homePage, projectPage, notFoundPage),

    // Handler for the default page.
    HomeHandler = Application.handler(function () {
        document.title = appTitle;
        pages.show(homePage);
        twoCents.get(this.get_path());
    }),
    
    // Handler for showing information about a single project.
    ProjectHandler = (function () {
        var
        // Path to the data file that contains a list of all projects.
        projectsPath = '/js/projects.json',
        // Variables for caching data.
        projects, project,
        
        // HTML elements. Fetched with find() since the div#project element
        // does not exist in the DOM at this point.
        viewList = projectPage.find('#view-select'),
        infoMenu = viewList.find('#select-info'),
        infoLink = infoMenu.find('a'),
        infoView = projectPage.find('#view-info'),
        demoMenu = viewList.find('#select-demo'),
        demoLink = demoMenu.find('a'),
        demoView = projectPage.find('#view-demo'),
        codeMenu = viewList.find('#select-code'),
        codeLink = codeMenu.find('a'),
        codeView = projectPage.find('#view-code'),
        downLink = codeView.find('#download-code'),
        
        name = projectPage.find('h1'),
        text = projectPage.find('p.description'),
        info = infoView.find('#info-content'),
        demo = demoView.find('#demo-content'),
        code = codeView.find('#code-content'),

        // Switcher for the views.
        views = new Switcher(infoView, demoView, codeView),
        
        // Helper function for loading and caching data from a URL and into an
        // element.
        load = function (into, obj, prop, isCode) {
            var
            cache = prop + 'Cache',
            cb = function (data) {
                obj[cache] = data;
                if (isCode)
                    into.text(data).prettify();
                else
                    into.html(data).find('pre').prettify();
            };

            if (cache in obj) {
                cb(obj[cache]);
            } else {
                cb('Loading...');
                $.get(obj[prop], null, cb);
            }
        };

        // Load the projects data.
        $.getJSON(projectsPath, function (data) {
            var section, project, id, list, i;

            // XXX: Possibly move the list generation to the HomeHandler.
            // Create list of projects on home page.
            projects = {};
            for (i = 0; i < data.length; i++) {
                section = data[i];

                // Header for the current section.
                $('<h2/>').text(section.name).appendTo(homePage);
                // Description of the current section.
                $('<p/>').text(section.text).appendTo(homePage);
                // List of projects in the current section.
                list = $('<ul/>').addClass('projects').appendTo(homePage);

                // Loop through all the projects in the current section.
                for (id in section.list) {
                    projects[id] = project = section.list[id];

                    // Add list item to the list of projects, with link and
                    // description text.
                    $('<li/>').append(
                        $('<a/>').hash('project/' + id).text(project.name),
                        '<br/>',
                        $('<em/>').text(project.text)
                    ).appendTo(list);
                }
            }
        });

        return Application.handler(function (id) {
            // Handle case where projects data has not yet been loaded.
            if (!projects) {
                // Run this handler again in 10 milliseconds.
                this.delay(10);
                return;
            }
            
            // Redirect to "Not found" page if the project doesn't exist.
            if (!(id in projects)) {
                this.redirect(NotFoundHandler);
                return;
            }
            
            // Update fields if a new project has been requested.
            if (!project || project != projects[id]) {
                // Set current project.
                project = projects[id];
                
                // Update project information.
                name.text(project.name);
                text.text(project.text);

                // Update view links.
                infoLink.hash('project/' + id + '?view=info');
                demoLink.hash('project/' + id + '?view=demo');
                codeLink.hash('project/' + id + '?view=code');
                
                // Update information view.
                if (project.info) {
                    // Load the file containing the information.
                    load(info, project, 'info');

                    project.defaultView = 'info';

                    infoMenu.removeClass('empty');
                } else {
                    info.html('<p>There is no information available for ' +
                              'this project.</p>');
                    infoMenu.addClass('empty');
                }

                // Update demonstration view.
                if (project.demo) {
                    // Load the file containing the demonstration.
                    load(demo, project, 'demo');

                    if (!project.defaultView) project.defaultView = 'demo';

                    demoMenu.removeClass('empty');
                } else {
                    demo.html('<p>There is no demonstration available for ' +
                              'this project.</p>');
                    demoMenu.addClass('empty');
                }
                
                // Update source code view.
                if (project.code) {
                    // Update the Download link.
                    downLink.attr('href', project.code);
                    downLink.parent().insertBefore(code);

                    // Load the file containing the source code.
                    load(code, project, 'code', true);

                    if (!project.defaultView) project.defaultView = 'code';

                    codeMenu.removeClass('empty');
                } else {
                    // Remove the Download link.
                    downLink.parent().remove();

                    code.text('There is no source code available for this ' +
                              'project.');
                    codeMenu.addClass('empty');
                }

                // Rare case, but has to be handled to avoid errors.
                if (!project.defaultView) project.defaultView = 'info';
            }
            
            // Show the selected view.
            var view = this.get_param('view', project.defaultView);
            switch (view) {
                case 'info':
                    views.show(infoView);
                    break;
                case 'demo':
                    views.show(demoView);
                    break;
                case 'code':
                    views.show(codeView);
                    break;
                default:
                    alert('Invalid view!');
                    $.hash.go('project/' + id);
                    return;
            }

            // Give the current view the CSS class "current".
            viewList.find('li').removeClass('current');
            viewList.find('#select-' + view).addClass('current');

            // Set page title.
            document.title = project.name + ' (' + view + ') in ' + appTitle;

            // Show the project page.
            pages.show(projectPage);
            twoCents.get(this.get_path());
        });
    })(),
    
    // Handler for showing an error when something can't be found.
    NotFoundHandler = Application.handler(function () {
        document.title = 'Path not found in ' + appTitle;
        notFoundPath.text(this.get_path());
        pages.show(notFoundPage);
        twoCents.get('not-found');
    }),

    // Create application for handling the site.
    app = new Application([
        ['^$', HomeHandler],
        ['^project/([^/]+)$', ProjectHandler],
        ['^.*$', NotFoundHandler]
    ]);

    // Set up application to use the hash as its path.
    $(document).hashchange(function (e, hash) {
        app.exec(hash);
    });
    
    // Enable the hash library.
    $.hash.init();

    // Set up physics.
    (function () {
        var
        // Physics variables.
        maxAtoms = 20,
        atomSizes = [40, 60, 40, 20, 60],

        // Create physics instance.
        physics = new Physics(0, -250, world.width(), world.height()),

        // Other variables used below.
        atoms, createInterval,
        sizes = [], html = '',
        i, size, dragging = -1,
        mouseX = 0, mouseY = 0;

        // Insert all images at once.
        for (i = 0; i < maxAtoms; i++) {
            sizes[i] = size = atomSizes[i % atomSizes.length];
            html += '<img alt="Atom" class="atom atom-' + size + '" ';
            html += 'src="/js/atom-' + size + '.gif"/>';
        }
        world.append(html);

        atoms = world.find('img.atom');

        // Allow dragging of atoms.
        atoms.mousedown(function () {
            if (dragging != -1) return false;
            dragging = atoms.index(this);
            atoms.eq(dragging).addClass('dragging');
            return false;
        });

        $(document).mouseup(function () {
            if (dragging == -1) return;
            atoms.eq(dragging).removeClass('dragging');
            dragging = -1;
        }).mousemove(function (e) {
            mouseX = e.pageX;
            mouseY = e.pageY;
        });

        // Update loop.
        setInterval(function () {
            var a;

            if (dragging > -1) {
                a = physics._get_atom(dragging);
                a.still = false;
                a.x += (mouseX - a.x) * 0.2;
                a.y += (mouseY - a.y) * 0.2;
            }

            physics.set_bounds(0, -250, world.width(), world.height());
            physics.step();
            for (var i = 0, l = physics.countAtoms(); i < l; i++) {
                a = physics._get_atom(i);
                if (a.still) continue;
                atoms.eq(i).css({'left': a.x, 'top': a.y});
            }
        }, 40);

        // Create new atoms.
        createInterval = setInterval(function () {
            var
            size = sizes[physics.countAtoms()],
            x = size / 2 + Math.random() * (world.width() - size),
            y = -100,

            // Create atom.
            aid = physics.atom(x, y, size / 2 + 1);

            if (physics.countAtoms() >= maxAtoms)
                clearInterval(createInterval);
        }, 250);
    })();

    JsRealm.demos = {};
    JsRealm.include = function (script) {
        for (var i = 0; i < included.length; i++)
            if (included[i] == script) return;

        $('body').prepend('<script src="' + script +
                          '" type="text/javascript"></script>');

        included.push(script);
    };
};

// Run the JsRealm function after the DOM has finished loading.
jQuery(JsRealm);
