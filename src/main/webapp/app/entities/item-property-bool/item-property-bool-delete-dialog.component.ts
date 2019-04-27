import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPropertyBool } from 'app/shared/model/item-property-bool.model';
import { ItemPropertyBoolService } from './item-property-bool.service';

@Component({
    selector: 'jhi-item-property-bool-delete-dialog',
    templateUrl: './item-property-bool-delete-dialog.component.html'
})
export class ItemPropertyBoolDeleteDialogComponent {
    itemPropertyBool: IItemPropertyBool;

    constructor(
        protected itemPropertyBoolService: ItemPropertyBoolService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPropertyBoolService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemPropertyBoolListModification',
                content: 'Deleted an itemPropertyBool'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-property-bool-delete-popup',
    template: ''
})
export class ItemPropertyBoolDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyBool }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemPropertyBoolDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemPropertyBool = itemPropertyBool;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-property-bool', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-property-bool', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
