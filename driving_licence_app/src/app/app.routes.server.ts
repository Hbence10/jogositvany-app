import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'searchPage/:type',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { type: "instructor" },
        { type: "school" }
      ];
    },
  },
  {
    path: 'profil/:type/:id',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { type: "user", id: "1" },
        { type: "school", id: "1" }
      ];
    },
  },
  {
    path: 'request/:owner',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { owner: "school" },
        { owner: "instructor" }
      ];
    },
  },
  {
    path: 'users/:userType',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return [
        { userType: "schoolStudent" },
        { userType: "instructors" },
        { userType: "instructorStudents" },
        { userType: "users" }
      ];
    },
  },

  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
