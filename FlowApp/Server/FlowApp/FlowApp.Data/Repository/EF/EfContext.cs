using FlowApp.Entity.Models;
using Microsoft.EntityFrameworkCore;

namespace FlowApp.Data.Repository.EF
{
    public class EFContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<Post> Posts { get; set; }
        public DbSet<Text> Texts { get; set; }
        public DbSet<Picture> Pictures { get; set; }
        public DbSet<Rating> Ratings { get; set; }
        public DbQuery<ViewPost> ViewPosts { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Server=tcp:......database.windows.net,1433;Initial Catalog=FlowAppDb;Persist Security Info=False;User ID=root;Password=.....;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;");

            //optionsBuilder.UseSqlServer("Server=(localdb)\\....; Database=FlowAppDb; Trusted_Connection=True; MultipleActiveResultSets=true");
        }

        [System.Obsolete]
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Query<ViewPost>().ToView("ViewPosts");
        }
    }
}