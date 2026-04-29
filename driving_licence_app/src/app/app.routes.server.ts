import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'searchPage/:type',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { type: "instructor" },
        { type: "school" }
      ]
    },
  },
  {
    path: 'request/:owner',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { type: "instructor" },
        { type: "school" }
      ]
    },
  },
  {
    path: 'profil/:type/:id',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { type: "user", id: '1' },
        { type: "school", id: '1' }
      ]
    },
  },

  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
